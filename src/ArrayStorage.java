import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int storageSize = 0; // Текущий размер массива

    void clear() {
        Arrays.fill(storage, null);
        storageSize = 0;
    }

    void save(Resume r) {
        if (storageSize == storage.length) return;
        storage[storageSize] = r;
        storageSize++;
    }

    Resume get(String uuid) {
        for (Resume resume : storage) {
            if (resume == null || Objects.equals(resume.uuid, uuid)) return resume;
        }
        return null;
    }

    void delete(String uuid) {
        int deletedPosition = -1; // позиция удаляемого элемента

        for (int i = 0; i < storageSize; i++) {
            if (Objects.equals(storage[i].uuid, uuid)) {
                deletedPosition = i;
                break;
            }
        }
        if (deletedPosition == -1) return;
        for (int i = deletedPosition; i < storageSize - 1; i++) {
            storage[i] = storage[i + 1];
        }
        storage[storageSize] = null;
        storageSize--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);
    }

    int size() {
        return storageSize;
    }
}
