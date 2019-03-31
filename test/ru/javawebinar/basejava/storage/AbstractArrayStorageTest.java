package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage arrayStorage) {
        storage = arrayStorage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
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
        assertEquals(UUID_2, storage.get(UUID_2).getUuid());
    }

    @Test
    public void update() {
        storage.update(storage.get(UUID_1));
        assertEquals(storage.get(UUID_1), storage.get(storage.get(UUID_1).getUuid()));
    }

    @Test
    public void save() {
        Resume resumeSaved = new Resume();
        storage.save(resumeSaved);
        assertEquals(resumeSaved, storage.get(resumeSaved.getUuid()));
    }

    @Test
    public void delete() {
        storage.delete(UUID_2);
        assertEquals(2, storage.size());
    }

    @Test
    public void getAll() {
        assertEquals(storage.size(), storage.getAll().length);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(storage.get(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void overflow() throws Exception {
        Field f = storage.getClass().getSuperclass().getDeclaredField("STORAGE_LIMIT");
        f.setAccessible(true);
        int storageSize = (int) f.get(storage);

        storage.clear();
        try {
            for (int i = 0; i < storageSize; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e){
            fail("Failed when filled the storage");
        }
        storage.save(new Resume());
    }
}