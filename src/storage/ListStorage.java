package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedStorageList = new ArrayList<>(storage);
        sortedStorageList.sort(Resume.COMPARE_BY_NAME);
        return sortedStorageList;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void doUpdate(Object searchKey, Resume r) {
        storage.set((int) searchKey, r);
    }

    @Override
    protected void doSave(Object searchKey, Resume r) {
        storage.add(r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (var it = storage.listIterator(); it.hasNext(); ) {
            Resume r = it.next();
            if (r.getUuid().equals(uuid)) return it.previousIndex();
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }
}
