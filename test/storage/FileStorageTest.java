package storage;

import storage.serialization.ObjectSerializer;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() throws NotDirectoryException, FileNotFoundException {
        super(new FileStorage(TEST_DIR, new ObjectSerializer()));
    }
}
