package storage;

import model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationStrategy {
    void doWrite(OutputStream os, Resume r) throws IOException;

    Resume doRead(InputStream is) throws IOException;
}
