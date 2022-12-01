package storage;

import model.Resume;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MapStorageAnotherOneTest extends AbstractStorageTest {
    public MapStorageAnotherOneTest() {
        super(new MapStorageAnotherOne());
    }

    @Test
    @Override
    void getAll() {
        Resume[] expectedArray = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        Resume[] checkingArray = storage.getAll();
        Arrays.sort(expectedArray);
        Arrays.sort(checkingArray);
        assertArrayEquals(expectedArray, checkingArray);
    }
}