package storage;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;

public class SerializableFileStorageTest extends AbstractStorageTest {
    public SerializableFileStorageTest() throws NotDirectoryException, FileNotFoundException {
        super(new SerializableFileStorage(TEST_DIR, new ObjectStreamSerializationStrategy()));
    }
}
