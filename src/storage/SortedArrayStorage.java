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
        for (int i = storageSize; i >= -index; i--) {
            storage[i] = storage[i - 1];
        }
        storage[-index - 1] = r;
    }

    @Override
    protected void deleteByIndex(int index) {
        System.arraycopy(storage,index+1,storage,index,storageSize-index-1);
        storage[storageSize] = null;
    }
}
