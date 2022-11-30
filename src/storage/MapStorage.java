package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapStorage extends AbstractStorage {

    protected HashMap<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedStorageList = new ArrayList<>( storage.values() );
        sortedStorageList.sort(Resume.COMPARE_BY_NAME);
        return sortedStorageList;
    }

    @Override
    protected void doUpdate(Object searchKey, Resume r) {
        storage.put((String) searchKey, r);
    }

    @Override
    protected void doSave(Object searchKey, Resume r) {
        storage.put((String) searchKey, r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }
}
