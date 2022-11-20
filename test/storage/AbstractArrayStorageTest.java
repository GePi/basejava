package storage;

import exceptions.ExistStorageException;
import exceptions.NotExistStorageException;
import exceptions.StorageException;
import model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest {
    static final Resume someResume = new Resume();
    static final String UUID1 = "uuid1";
    static final String UUID2 = "uuid2";
    static final String UUID3 = "uuid3";
    static final String NOT_EXIST_UUID = "NotExistUuid";

    int mockStorageInitialSize;

    private final AbstractArrayStorage storage;

    public AbstractArrayStorageTest(AbstractArrayStorage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void fillMockStorage() {
        storage.save(new Resume(UUID1));
        storage.save(new Resume(UUID2));
        storage.save(new Resume(UUID3));
        mockStorageInitialSize = 3;
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void afterClearAllElementsIsNull() {
        storage.clear();
        for (var resume : storage.storage) {
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
        storage.save(someResume);
        Resume updatedResume = new Resume(someResume.getUuid());
        //TODO здесь будет изменение полей (когда они появятся), а пока assertEquals (а в итоге Resume.equals) проверит, что в массив прописался именно этот объект (по совпадению ссылки)
        storage.update(updatedResume);
        assertEquals(storage.get(updatedResume.getUuid()), updatedResume);
    }

    @Test
    void save() {
        var storageSize = storage.storageSize;
        storage.save(someResume);
        assertEquals(storage.get(someResume.getUuid()), someResume);
        assertEquals(storageSize, storage.storageSize - 1);
    }

    @Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(storage.get(UUID1)));
    }

    @Test
    void saveIndexOutOfBounds() {
        try {
            for (int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            assertNull(e,"Корректное сохранение");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }

    @Test
    void get() {
        assertNotNull(storage.get(UUID1));
        assertThrows(NotExistStorageException.class, () -> storage.get(NOT_EXIST_UUID));
    }

    @Test
    void delete() {
        var storageSize = storage.storageSize;
        storage.delete(UUID1);
        assertEquals(storageSize - 1, storage.storageSize);
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID1));
    }

    @Test
    void getAll() {
        Resume[] resumeArray = storage.getAll();
        assertEquals(mockStorageInitialSize, resumeArray.length);
    }

    @Test
    void size() {
        assertEquals(storage.storageSize, storage.size());
        assertEquals(mockStorageInitialSize, storage.size());
    }
}