package storage;

import exceptions.StorageException;
import model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    protected final Path directory;
    protected SerializationStrategy serialization;

    public PathStorage(Path directory, SerializationStrategy serialization) throws NotDirectoryException, FileNotFoundException {
        Objects.requireNonNull(directory);
        Objects.requireNonNull(serialization);

        if (!Files.exists(directory)) {
            throw new FileNotFoundException(directory.toAbsolutePath().toString());
        }
        if (!Files.isDirectory(directory)) {
            throw new NotDirectoryException(directory.toAbsolutePath().toString());
        }
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath().toString());
        }
        this.directory = directory;
        this.serialization = serialization;
    }

    @Override
    protected void doUpdate(Path path, Resume r) {
        try {
            serialization.doWrite(new BufferedOutputStream(new FileOutputStream(path.toFile())), r);
        } catch (IOException e) {
            throw new StorageException("The file " + path.getFileName() + " could not be changed", r.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Path path, Resume r) {
        try {
            Files.createFile(path);
            doUpdate(path, r);
        } catch (IOException e) {
            throw new StorageException("The file " + path.getFileName() + " could not be created", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serialization.doRead(new BufferedInputStream(new FileInputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("The file " + path.getFileName() + " could not be read", "dummy", e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("The file " + path.getFileName() + " not deleted");
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        return getDirStream().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    public void clear() {
        getDirStream().forEach(this::doDelete);
    }

    @Override
    public Resume[] getAll() {
        return getDirStream().map(this::doGet).toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return (int) getDirStream().count();
    }

    public Stream<Path> getDirStream() {
        Stream<Path> list;
        try {
            list = Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Storage directory reading error");
        }
        return list;
    }

    public void setSerializationStrategy(SerializationStrategy serializationStrategy) {
        this.serialization = serializationStrategy;
    }
}
