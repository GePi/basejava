package storage;

import model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static class resumeComparator implements Comparator<Resume> {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    }

    private final Comparator<Resume> comparator = new Comparator<>() {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    };

    @Override
    protected int getIndex(String uuid) {
        var searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, storageSize, searchKey, comparator);
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
