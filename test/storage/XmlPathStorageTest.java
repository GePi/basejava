package storage;

import storage.serialization.XmlSerializer;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() throws NotDirectoryException, FileNotFoundException {
        super(new PathStorage(TEST_DIR.toPath(), new XmlSerializer()));
    }
}
