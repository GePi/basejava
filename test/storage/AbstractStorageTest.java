package storage;

import exceptions.ExistStorageException;
import exceptions.NotExistStorageException;
import model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractStorageTest {
    static final String UUID1 = "uuid1";
    static final String UUID2 = "uuid2";
    static final String UUID3 = "uuid3";
    static final String NOT_EXIST_UUID = "NotExistUuid";
    static final Resume RESUME_1 = new Resume(UUID1, "Пушкин Александр Сергеевич");
    static final Resume RESUME_2 = new Resume(UUID2, "Ахматова Анна Андреевна");
    static final Resume RESUME_3 = new Resume(UUID3, "Булгаков Михаил Афанасьевич");
    static final Resume RESUME_4 = new Resume();
    protected int storageInitialSize;

    protected final AbstractStorage storage;

    public AbstractStorageTest(AbstractStorage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void fillMockStorage() {
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        storageInitialSize = 3;
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(new Resume[]{}, storage.getAll());
    }

    @Test
    void afterClearAllElementsIsNull() {
        storage.clear();
        for (var resume : storage.getAll()) {
            if (resume != null) {
                fail("Неудовлетворительная очистка массива");
            }
        }
    }

    @Test
    void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume()));
    }

    @Test
    void update() {
        storage.save(RESUME_4);
        Resume updatedResume = new Resume(RESUME_4.getUuid());
        storage.update(updatedResume);
        assertSame(storage.get(updatedResume.getUuid()), updatedResume);
    }

    @Test
    void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(storage.get(UUID1)));
    }

    @Test
    void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get(NOT_EXIST_UUID));
    }

    void assertGet(Resume r) {
        assertEquals(storage.get(r.getUuid()), r);
    }

    @Test
    void delete() {
        storage.delete(UUID1);
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID1));
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(NOT_EXIST_UUID));
    }

    @Test
    void getAll() {
        assertArrayEquals(new Resume[]{RESUME_1, RESUME_2, RESUME_3}, storage.getAll());
    }

    @Test
    void getAllList() {
        Resume[] resumeSortedArray = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        Arrays.sort(resumeSortedArray, Resume.COMPARE_BY_NAME);
        assertArrayEquals(resumeSortedArray, storage.getAllSorted().toArray());
    }

    @Test
    void size() {
        assertSize(storageInitialSize);
    }

    void assertSize(int expectedSize) {
        assertEquals(expectedSize, storage.size());
    }
}