package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SearchKey;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElement(Resume resume, SearchKey searchKey) {
        storage[size] = resume;
    }

    @Override
    protected void deleteElement(SearchKey searchKey) {
        storage[searchKey.getIndex()] = storage[size - 1];
    }

    @Override
    protected SearchKey getSearchKey(String uuid) {
        SearchKey searchKey = new SearchKey();
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                searchKey.setIndex(i);
                break;
            }
        }
        return searchKey;
    }
}
