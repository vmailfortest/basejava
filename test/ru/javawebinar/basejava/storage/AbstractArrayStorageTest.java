package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);

    AbstractArrayStorageTest(Storage arrayStorage) {
        storage = arrayStorage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        assertEquals(RESUME_2, storage.get(UUID_2));
    }

    @Test
    public void update() {
        Resume updateResume = new Resume(UUID_1);
        storage.update(updateResume);
        assertEquals(updateResume, storage.get(UUID_1));
    }

    @Test
    public void save() {
        Resume resumeSave = new Resume("resumeSave");
        storage.save(resumeSave);
        assertEquals(resumeSave, storage.get(resumeSave.getUuid()));
        assertEquals(4, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        try {
            storage.delete(UUID_2);
            assertEquals(2, storage.size());
        } catch (StorageException e) {
            fail("Failed to deleted element");
        }
        storage.get(UUID_2);
    }

    @Test
    public void getAll() {
        Resume[] expected =  new Resume[]{RESUME_1,RESUME_2,RESUME_3};
        assertEquals(storage.size(), storage.getAll().length);
        assertArrayEquals(expected, storage.getAll());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        Field f = storage.getClass().getSuperclass().getDeclaredField("STORAGE_LIMIT");
        f.setAccessible(true);
        int storageSize = (int) f.get(storage);

        storage.clear();
        try {
            for (int i = 0; i < storageSize; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            fail("Failed when filled the storage");
        }
        storage.save(new Resume());
    }
}