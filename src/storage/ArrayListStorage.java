package storage;

import exceptions.ExistStorageException;
import exceptions.NotExistStorageException;
import model.Resume;

import java.util.ArrayList;

public class ArrayListStorage extends AbstractStorage {

    protected ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
        int i = storage.indexOf(r);
        if (i < 0) throw new NotExistStorageException(r.getUuid());
        storage.set(i, r);
    }

    @Override
    public void save(Resume r) {
        int i = storage.indexOf(r);
        if (i >= 0) throw new ExistStorageException(r.getUuid());
        storage.add(r);
    }

    @Override
    public Resume get(String uuid) {
        for (Resume r : storage) {
            if (r.getUuid().equals(uuid)) {
                return r;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        Resume searchKey = new Resume(uuid);
        if (!storage.remove(searchKey)) throw new NotExistStorageException(uuid);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
