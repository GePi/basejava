package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        var searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, storageSize, searchKey);
    }

    @Override
    protected void saveToIndex(Resume r, int index) {
        int insInd = -index - 1;
        System.arraycopy(storage, insInd, storage, insInd + 1, storageSize - insInd);
        storage[insInd] = r;
    }

    @Override
    protected void deleteByIndex(int index) {
        System.arraycopy(storage, index + 1, storage, index, storageSize - index - 1);
        storage[storageSize] = null;
    }
}
