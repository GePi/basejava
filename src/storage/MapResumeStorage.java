package storage;

import model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void doUpdate(Resume searchKey, Resume r) {
        storage.put(searchKey.getUuid(), r);
    }

    @Override
    protected void doSave(Resume searchKey, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void doDelete(Resume searchKey) {
        storage.remove((searchKey).getUuid());
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return Objects.nonNull(searchKey);
    }
}