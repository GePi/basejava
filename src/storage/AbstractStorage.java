package storage;

import exceptions.ExistStorageException;
import exceptions.NotExistStorageException;
import model.Resume;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class AbstractStorage implements Storage {
    private static final Logger LOG = Logger.getLogger("AbstractStorage");

    static {
        LOG.setLevel(Level.SEVERE);
    }

    abstract protected void doUpdate(Object searchKey, Resume r);

    abstract protected void doSave(Object searchKey, Resume r);

    abstract protected Resume doGet(Object searchKey);

    abstract protected void doDelete(Object searchKey);

    abstract protected List<Resume> doCopyAll();

    abstract protected Object getSearchKey(String uuid);

    abstract protected boolean isExist(Object searchKey);

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        Object searchKey = getExistSearchKey(r.getUuid());
        doUpdate(searchKey, r);
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        Object searchKey = getNotExistSearchKey(r.getUuid());
        doSave(searchKey, r);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        Object searchKey = getExistSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        Object searchKey = getExistSearchKey(uuid);
        doDelete(searchKey);
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> resumeList = doCopyAll();
        resumeList.sort(Resume.COMPARE_BY_NAME);
        return resumeList;
    }

    private Object getExistSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            var e = new NotExistStorageException(uuid);
            LOG.log(Level.SEVERE, String.format("Resume search key %s is not exist", searchKey), e);
            throw e;
        }
        return searchKey;
    }

    private Object getNotExistSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            var e = new ExistStorageException(uuid);
            LOG.log(Level.SEVERE, String.format("Resume search key %s is already exist", searchKey), e);
            throw e;
        }
        return searchKey;
    }
}