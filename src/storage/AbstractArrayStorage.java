package storage;

import exceptions.StorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    protected abstract int getIndex(String uuid);

    protected abstract void deleteByIndex(int index);

    protected abstract void saveToIndex(Resume r, int index);

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOf(storage, storageSize));
    }

    public int size() {
        return storageSize;
    }

    protected void doUpdate(Integer searchKey, Resume r) {
        storage[searchKey] = r;
    }

    protected void doSave(Integer searchKey, Resume r) {
        if (storageSize == STORAGE_LIMIT) {
            throw new StorageException(String.format("Превышено максимальное количество (%d) хранимых резюме", STORAGE_LIMIT), r.getUuid());
        }
        saveToIndex(r, searchKey);
        storageSize++;
    }

    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    protected void doDelete(Integer searchKey) {
        deleteByIndex(searchKey);
        storageSize--;
    }

    protected Integer getSearchKey(String uuid) {
        return getIndex(uuid);
    }

    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }
}
