package com.urise.storage;

import com.urise.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int storageSize = 0; // Текущий размер массива

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    public void update(Resume r) {
        if (find(r.getUuid()) == null) {
            System.out.printf("Резюме не обновлено: объект с uuid = %s не найден в массиве \n", r.getUuid());
        }
    }

    public void save(Resume r) {
        if (find(r.getUuid()) != null) {
            System.out.printf("Резюме не добавлено: объект с uuid = %s уже существует в массиве \n", r.getUuid());
        } else if (storageSize == storage.length) {
            System.out.printf("Резюме не добавлено: превышено максимальное количество (%d) хранимых резюме \n", storage.length);
        } else {
            storage[storageSize] = r;
            storageSize++;
        }
    }

    private Resume find(String uuid) {
        int findPos = findPosition(uuid);

        if (findPos < 0) {
            return null;
        } else {
            return storage[findPos];
        }
    }

    private int findPosition(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) return i;
        }
        return -1;
    }
    public Resume get(String uuid) {
        Resume resume = find(uuid);
        if (resume == null) {
            System.out.printf("Резюме с uuid = %s не найдено \n", uuid);
        }
        return resume;
    }

    public void delete(String uuid) {
        int deletedPosition = findPosition(uuid); // позиция удаляемого элемента

        if (deletedPosition == -1) {
            System.out.printf("Резюме не удалено: объект с uuid = %s не найден в массиве \n", uuid);
        } else {
            storage[deletedPosition] = storage[storageSize-1];
            storage[storageSize-1] = null;
            storageSize--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);
    }

    public int size() {
        return storageSize;
    }
}
