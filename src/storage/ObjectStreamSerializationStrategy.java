package storage;

import model.Resume;

import java.io.*;

public class ObjectStreamSerializationStrategy implements SerializationStrategy {
    @Override
    public void writeSerialized(OutputStream os, Resume r) throws IOException {
        try (var objectStream = new ObjectOutputStream(os)) {
            objectStream.writeObject(r);
        }
    }

    @Override
    public Resume readSerialized(InputStream is) throws IOException {
        try (var objectStream = new ObjectInputStream(is)) {
            return (Resume) objectStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
