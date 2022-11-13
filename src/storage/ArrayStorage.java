package storage;

import model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void saveToIndex(Resume r, int index) {
        storage[storageSize] = r;
    }

    @Override
    protected void deleteByIndex(int index) {
        storage[index] = storage[storageSize - 1];
        storage[storageSize - 1] = null;
    }
}

