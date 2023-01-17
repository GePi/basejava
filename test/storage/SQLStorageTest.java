package storage;

import utils.Config;

class SQLStorageTest extends AbstractStorageTest {
    public SQLStorageTest() {
        super(Config.getInstance().getStorage());
    }
}