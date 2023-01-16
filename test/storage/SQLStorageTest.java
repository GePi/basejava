package storage;

import utils.Config;

class SQLStorageTest extends AbstractStorageTest {
    public SQLStorageTest() {
        super(new SqlStorage(
                Config.getInstance().getDbUrl(),
                Config.getInstance().getDbUser(),
                Config.getInstance().getDbPassword()));
    }
}