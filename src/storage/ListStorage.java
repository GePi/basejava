package storage;

import exceptions.NotExistStorageException;
import exceptions.ExistStorageException;
import model.Resume;

import java.util.LinkedList;

public class ListStorage extends AbstractStorage {
    protected LinkedList<Resume> storage = new LinkedList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
        for (var listIterator = storage.listIterator(); listIterator.hasNext(); ) {
            if (listIterator.next().equals(r)) {
                listIterator.set(r);
                return;
            }
        }
        throw new NotExistStorageException(r.getUuid());
    }

    @Override
    public void save(Resume r) {
        if (storage.contains(r)) throw new ExistStorageException(r.getUuid());
        storage.add(r);
    }

    @Override
    public Resume get(String uuid) {
        for (Resume resume : storage) {
            if (resume.getUuid().equals(uuid)) {
                return resume;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        for (var iterator = storage.iterator(); iterator.hasNext(); ) {
            Resume resume = iterator.next();
            if (resume.getUuid().equals(uuid)) {
                iterator.remove();
                return;
            }
        }
        throw new NotExistStorageException(uuid);
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
