package storage;

import exceptions.ExistStorageException;
import exceptions.NotExistStorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {
    abstract protected void doUpdate(Object searchKey, Resume r);

    abstract protected void doSave(Object searchKey, Resume r);

    abstract protected Resume doGet(Object searchKey);

    abstract protected void doDelete(Object searchKey);

    abstract protected Object getSearchKey(String uuid);

    abstract protected boolean isExist(Object searchKey);

    @Override
    public void update(Resume r) {
        Object searchKey = getExistSearchKey(r.getUuid());
        doUpdate(searchKey, r);
    }

    @Override
    public void save(Resume r) {
        Object searchKey = getNotExistSearchKey(r.getUuid());
        doSave(searchKey, r);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistSearchKey(uuid);
        doDelete(searchKey);
    }

    private Object getExistSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}