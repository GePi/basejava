package storage;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() throws NotDirectoryException, FileNotFoundException {
        super(new ObjectStreamStorage(TEST_DIR));
    }
}
