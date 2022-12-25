package storage;

import storage.serialization.JsonSerializer;
import storage.serialization.XmlSerializer;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() throws NotDirectoryException, FileNotFoundException {
        super(new PathStorage(TEST_DIR.toPath(), new JsonSerializer()));
    }
}
