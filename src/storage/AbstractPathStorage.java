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

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    protected final Path directory;

    public AbstractPathStorage(Path directory) throws NotDirectoryException, FileNotFoundException {
        Objects.requireNonNull(directory);
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
    }

    protected abstract Resume doReadFile(InputStream is) throws IOException;

    protected abstract void doWriteFile(OutputStream os, Resume r) throws IOException;

    @Override
    protected void doUpdate(Path path, Resume r) {
        try {
            doWriteFile(new BufferedOutputStream(new FileOutputStream(path.toFile())), r);
        } catch (IOException e) {
            throw new StorageException("The file " + path.getFileName() + " could not be changed", r.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Path path, Resume r) {
        try {
            Files.createFile(path);
            doWriteFile(new BufferedOutputStream(new FileOutputStream(path.toFile())), r);
        } catch (IOException e) {
            throw new StorageException("The file " + path.getFileName() + " could not be created", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doReadFile(new BufferedInputStream(new FileInputStream(path.toFile())));
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
        try (Stream<Path> list = Files.list(directory)) {
            return list.map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Storage directory reading error");
        }
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
        try (Stream<Path> list = Files.list(directory)) {
            list.forEach(this::doDelete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resume[] getAll() {
        try (Stream<Path> list = Files.list(directory)) {
            return list.map(this::doGet).toArray(Resume[]::new);
        } catch (IOException e) {
            throw new StorageException("Storage directory reading error");
        }
    }

    @Override
    public int size() {
        try (Stream<Path> list = Files.list(directory)) {
            return (int) list.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
