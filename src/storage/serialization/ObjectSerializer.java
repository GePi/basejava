package storage.serialization;

import model.Resume;

import java.io.*;

public class ObjectSerializer implements SerializationStrategy {
    @Override
    public void doWrite(OutputStream os, Resume r) throws IOException {
        try (var objectStream = new ObjectOutputStream(os)) {
            objectStream.writeObject(r);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (var objectStream = new ObjectInputStream(is)) {
            return (Resume) objectStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
