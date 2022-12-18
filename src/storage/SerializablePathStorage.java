package storage;

import model.Resume;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;

public class SerializablePathStorage extends AbstractPathStorage {
    protected SerializationStrategy serializationStrategy;

    public SerializablePathStorage(Path directory, SerializationStrategy ss) throws NotDirectoryException, FileNotFoundException {
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
