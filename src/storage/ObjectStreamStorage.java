package storage;

import model.Resume;

import java.io.*;
import java.nio.file.NotDirectoryException;

public class ObjectStreamStorage extends AbstractFileStorage {
    public ObjectStreamStorage(File directory) throws NotDirectoryException, FileNotFoundException {
        super(directory);
    }

    @Override
    protected Resume doReadFile(InputStream is) throws IOException {
        try (var objectStream = new ObjectInputStream(is)) {
            return (Resume) objectStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doWriteFile(OutputStream os, Resume r) throws IOException {
        try (var objectStream = new ObjectOutputStream(os)) {
            objectStream.writeObject(r);
        }
    }
}
