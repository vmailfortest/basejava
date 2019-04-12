package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void deleteResume(String uuid, int index) {
        deleteElement(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertElement(resume, index);
        size++;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected Resume getResume(String uuid, int index) {
        return storage[index];
    }

    @Override
    protected void replaceResume(Resume resume, int index) {
        storage[index] = resume;
    }

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void deleteElement(int index);

}
