package storage.serialization;

import java.io.IOException;

public interface IterableReader {
    public void readElement() throws IOException;
}
