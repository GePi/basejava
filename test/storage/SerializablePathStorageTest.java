package storage;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;

public class SerializablePathStorageTest extends AbstractStorageTest {
    public SerializablePathStorageTest() throws NotDirectoryException, FileNotFoundException {
        super(new SerializablePathStorage(TEST_DIR.toPath(), new ObjectStreamSerializationStrategy()));
    }
}
