package storage;

import exceptions.StorageException;
import model.Resume;

import java.io.*;
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

    protected abstract Resume doReadFile(InputStream is) throws IOException;

    protected abstract void doWriteFile(OutputStream os, Resume r) throws IOException;

    @Override
    protected void doUpdate(File file, Resume r) {
        try {
            doWriteFile(new BufferedOutputStream(new FileOutputStream(file)), r);
        } catch (IOException e) {
            throw new StorageException("The file " + file.getPath() + " could not be changed", r.getUuid(), e);
        }
    }

    @Override
    protected void doSave(File file, Resume r) {
        try {
            if (file.createNewFile()) {
                doWriteFile(new BufferedOutputStream(new FileOutputStream(file)), r);
            } else {
                throw new StorageException("The file " + file.getPath() + " already exist", r.getUuid());
            }
        } catch (IOException e) {
            throw new StorageException("The file " + file.getPath() + " could not be created", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doReadFile(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("The file " + file.getPath() + " could not be read", "dummy", e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("The file " + file.getPath() + " not deleted");
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        var listFiles = directory.listFiles();
        if (listFiles == null) {
            throw new StorageException("Storage directory reading error");
        }
        ArrayList<Resume> resume = new ArrayList<>((int) directory.length());
        for (File file : listFiles) {
            resume.add(doGet(file));
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
        if (listFiles == null) {
            throw new StorageException("Storage directory reading error");
        }
        for (var file : listFiles) {
            doDelete(file);
        }
    }

    @Override
    public Resume[] getAll() {
        var files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Storage directory reading error");
        }
        Resume[] resumeArr = new Resume[files.length];
        for (int i = 0; i < files.length; i++) {
            resumeArr[i] = doGet(files[i]);
        }
        return resumeArr;
    }

    @Override
    public int size() {
        var files = directory.list();
        if (files == null) {
            throw new StorageException("Storage directory reading error");
        }
        return files.length;
    }
}
