package storage;

import storage.serialization.DataSerializer;
import storage.serialization.JsonSerializer;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() throws NotDirectoryException, FileNotFoundException {
        super(new PathStorage(TEST_DIR.toPath(), new DataSerializer()));
    }
}
