package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume r) {
        storage.set(searchKey, r);
    }

    @Override
    protected void doSave(Integer searchKey, Resume r) {
        storage.add(r);
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        storage.remove(searchKey.intValue());
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (var it = storage.listIterator(); it.hasNext(); ) {
            Resume r = it.next();
            if (r.getUuid().equals(uuid)) return it.previousIndex();
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }
}
