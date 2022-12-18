package storage;

import model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationStrategy {
    void writeSerialized(OutputStream os, Resume r) throws IOException;

    Resume readSerialized(InputStream is) throws IOException;
}
