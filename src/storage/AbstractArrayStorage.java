package storage;

import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            System.out.printf("Резюме не обновлено: объект с uuid = %s не найден в массиве \n", r.getUuid());
        } else {
            storage[index] = r;
        }
    }

    public void save(Resume r) {
        if (storageSize == STORAGE_LIMIT) {
            System.out.printf("Резюме не добавлено: превышено максимальное количество (%d) хранимых резюме \n", STORAGE_LIMIT);
            return;
        }
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            System.out.printf("Резюме не добавлено: объект с uuid = %s уже существует в массиве \n", r.getUuid());
            return;
        }
        saveToIndex(r, index);
        storageSize++;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.printf("Резюме с uuid = %s не найдено \n", uuid);
            return null;
        } else {
            return storage[index];
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.printf("Резюме не удалено: объект с uuid = %s не найден в массиве \n", uuid);
        } else {
            deleteByIndex(index);
            storageSize--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);
    }

    public int size() {
        return storageSize;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void deleteByIndex(int index);

    protected abstract void saveToIndex(Resume r, int index);
}
