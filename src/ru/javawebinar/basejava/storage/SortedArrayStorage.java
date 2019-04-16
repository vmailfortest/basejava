package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElement(Resume resume, int searchKey) {
        searchKey = -searchKey - 1;
        System.arraycopy(storage, searchKey, storage, searchKey + 1, size - searchKey);
        storage[searchKey] = resume;
    }

    @Override
    protected void deleteElement(int searchKey) {
        System.arraycopy(storage, searchKey + 1, storage, searchKey, size - searchKey - 1);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchResume = new Resume(uuid);
        return (Object) Arrays.binarySearch(storage, 0, size, searchResume);
    }
}