package storage;

import storage.serialization.ObjectSerializer;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() throws NotDirectoryException, FileNotFoundException {
        super(new PathStorage(TEST_DIR.toPath(), new ObjectSerializer()));
    }
}
