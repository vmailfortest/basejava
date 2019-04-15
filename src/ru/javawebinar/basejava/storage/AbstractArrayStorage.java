package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SearchKey;

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
    protected void deleteResume(SearchKey searchKey) {
        deleteElement(searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void insertResume(Resume resume, SearchKey searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertElement(resume, searchKey);
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
    protected Resume getResume(SearchKey searchKey) {
        return storage[searchKey.getIndex()];
    }

    @Override
    protected void replaceResume(Resume resume, SearchKey searchKey) {
        storage[searchKey.getIndex()] = resume;
    }

    protected boolean verifySearchKey(SearchKey searchKey) {
        if (searchKey.getIndex() < 0) {
            return false;
        }
        return true;
    }

    protected abstract void insertElement(Resume resume, SearchKey searchKey);

    protected abstract void deleteElement(SearchKey searchKey);

}
