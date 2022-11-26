package storage;

import exceptions.ExistStorageException;
import exceptions.NotExistStorageException;
import model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {

    protected HashMap<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
        if (!storage.containsKey(r.getUuid())) throw new NotExistStorageException(r.getUuid());
        storage.put(r.getUuid(), r);
    }

    @Override
    public void save(Resume r) {
        if (storage.containsKey(r.getUuid())) throw new ExistStorageException(r.getUuid());
        storage.put(r.getUuid(), r);
    }

    @Override
    public Resume get(String uuid) {
        Resume r = storage.get(uuid);
        if (r == null) throw new NotExistStorageException(uuid);
        return r;
    }

    @Override
    public void delete(String uuid) {
        Resume r = storage.remove(uuid);
        if (r == null) throw new NotExistStorageException(uuid);
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
