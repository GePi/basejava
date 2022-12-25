package storage.serialization;

import model.Resume;
import utils.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonSerializer implements SerializationStrategy {
    @Override
    public void doWrite(OutputStream os, Resume r) throws IOException {
        try (var osw = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            new JsonParser().write(r, osw);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (var isr = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return new JsonParser().read(isr, Resume.class);
        }
    }
}
