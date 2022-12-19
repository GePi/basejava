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
        FileStorageTest.class}
)
public class AllStorageTest {
}


