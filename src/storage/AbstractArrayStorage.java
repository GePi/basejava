package storage;

import exceptions.StorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
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

    protected void doUpdate(Object searchKey, Resume r) {
        storage[(int) searchKey] = r;
    }

    protected void doSave(Object searchKey, Resume r) {
        if (storageSize == STORAGE_LIMIT) {
            throw new StorageException(String.format("Превышено максимальное количество (%d) хранимых резюме", STORAGE_LIMIT), r.getUuid());
        }
        saveToIndex(r, (int) searchKey);
        storageSize++;
    }

    protected Resume doGet(Object searchKey) {
        return storage[(int) searchKey];
    }

    protected void doDelete(Object searchKey) {
        deleteByIndex((int) searchKey);
        storageSize--;
    }

    protected Object getSearchKey(String uuid) {
        return getIndex(uuid);
    }

    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }
}
