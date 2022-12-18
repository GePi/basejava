package storage;

import model.Resume;

import java.io.*;
import java.nio.file.NotDirectoryException;

public class SerializableFileStorage extends AbstractFileStorage {
    protected SerializationStrategy serializationStrategy;

    public SerializableFileStorage(File directory, SerializationStrategy ss) throws NotDirectoryException, FileNotFoundException {
        super(directory);
        this.serializationStrategy = ss;
    }

    public void setSerializationStrategy(SerializationStrategy serializationStrategy) {
        this.serializationStrategy = serializationStrategy;
    }

    @Override
    protected Resume doReadFile(InputStream is) throws IOException {
        return serializationStrategy.readSerialized(is);
    }

    @Override
    protected void doWriteFile(OutputStream os, Resume r) throws IOException {
        serializationStrategy.writeSerialized(os, r);
    }
}
