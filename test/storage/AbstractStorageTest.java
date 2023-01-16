package storage;

import TestData.ResumeTestData;
import exceptions.ExistStorageException;
import exceptions.NotExistStorageException;
import model.Resume;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractStorageTest {
    static final File TEST_DIR = Config.getInstance().getStorageDir();
    static final String UUID1 = "uuid1";
    static final String UUID2 = "uuid2";
    static final String UUID3 = "uuid3";
    static final String UUID4 = "uuid4";
    static final String NOT_EXIST_UUID = "NotExistUuid";
    static final Resume RESUME_1 = ResumeTestData.createResume(UUID1, "Пушкин Александр Сергеевич");
    static final Resume RESUME_2 = ResumeTestData.createResume(UUID2, "Ахматова Анна Андреевна");
    static final Resume RESUME_3 = ResumeTestData.createResume(UUID3, "Булгаков Михаил Афанасьевич");
    static final Resume RESUME_4 = ResumeTestData.createResume(UUID4, "Есенин Сергей Александрович");
    protected int storageInitialSize;
    protected final Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void fillMockStorage() {
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        storageInitialSize = 3;
    }

    @AfterEach
    void clearDir() {
        storage.clear();
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        assertEquals(new ArrayList<Resume>(), storage.getAllSorted());
    }

    @Test
    void afterClearAllElementsIsNull() {
        storage.clear();
        for (var resume : storage.getAllSorted()) {
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
        Resume updatedResume = new Resume(RESUME_4.getUuid(), RESUME_4.getFullName());
        storage.update(updatedResume);
        assertEquals(storage.get(updatedResume.getUuid()), updatedResume);
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