package storage;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({MapStorageTest.class, MapStorageAnotherOneTest.class, ListStorageTest.class, ArrayStorageTest.class}
)
public class AllStorageTest {
}


