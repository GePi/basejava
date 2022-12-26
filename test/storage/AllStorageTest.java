package storage;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        MapStorageTest.class,
        MapResumeStorageTest.class,
        ListStorageTest.class,
        ArrayStorageTest.class,
        PathStorageTest.class,
        FileStorageTest.class,
        XmlPathStorageTest.class,
        JsonPathStorageTest.class,
        DataPathStorageTest.class}
)
public class AllStorageTest {
}


