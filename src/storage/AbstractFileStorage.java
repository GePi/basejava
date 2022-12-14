package storage;

import exceptions.StorageException;
import model.Resume;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    protected final File directory;

    public AbstractFileStorage(File directory) throws NotDirectoryException, FileNotFoundException {
        Objects.requireNonNull(directory);
        if (!directory.exists()) {
            throw new FileNotFoundException(directory.getAbsolutePath());
        }
        if (!directory.isDirectory()) {
            throw new NotDirectoryException(directory.getAbsolutePath());
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath());
        }
        this.directory = directory;
    }

    protected abstract Resume doReadFile(File file) throws IOException;

    protected abstract void doWriteFile(File file, Resume r) throws IOException;

    @Override
    protected void doUpdate(File file, Resume r) {
        try {
            doWriteFile(file, r);
        } catch (IOException e) {
            throw new StorageException("The file " + file.getPath() + " could not be changed", r.getUuid(), e);
        }

    }

    @Override
    protected void doSave(File file, Resume r) {
        try {
            file.createNewFile();
            doWriteFile(file, r);
        } catch (IOException e) {
            throw new StorageException("The file " + file.getPath() + " could not be created", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doReadFile(file);
        } catch (IOException e) {
            throw new StorageException("The file " + file.getPath() + " could not be read", "dummy", e);
        }
    }

    @Override
    protected void doDelete(File file) {
        file.deleteOnExit();
    }

    @Override
    protected List<Resume> doCopyAll() {
        var listFiles = directory.listFiles();
        if (listFiles == null || listFiles.length == 0) {
            return new ArrayList<>();
        }
        ArrayList<Resume> resume = new ArrayList<>((int) directory.length());
        for (File file : listFiles) {
            try {
                resume.add(doReadFile(file));
            } catch (IOException e) {
                throw new StorageException("The file " + file.getPath() + " could not be read", "dummy", e);
            }
        }
        return resume;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public void clear() {
        var listFiles = directory.listFiles();
        if (listFiles != null) {
            for (var file : listFiles) {
                file.delete();
            }
        }
    }

    @Override
    public Resume[] getAll() {
        var files = directory.listFiles();
        if (files == null || files.length == 0) {
            return new Resume[]{};
        }
        Resume[] resumeArr = new Resume[files.length];
        for (int i = 0; i < files.length; i++) {
            try {
                resumeArr[i] = doReadFile(files[i]);
            } catch (IOException e) {
                throw new StorageException("The file " + files[i].getPath() + " could not be read", "dummy", e);
            }
        }
        return resumeArr;
    }

    @Override
    public int size() {
        return (int) directory.length();
    }
}
