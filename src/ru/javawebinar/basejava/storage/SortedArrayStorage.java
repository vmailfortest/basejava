package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SearchKey;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElement(Resume resume, SearchKey searchKey) {
        int index = -searchKey.getIndex() - 1;
        System.arraycopy(storage, index, storage, index + 1,size - index);
        storage[index] = resume;
    }

    @Override
    protected void deleteElement(SearchKey searchKey) {
        System.arraycopy(storage, searchKey.getIndex() + 1, storage,
                searchKey.getIndex(), size - searchKey.getIndex() - 1);
    }

    @Override
    protected SearchKey getSearchKey(String uuid) {
        SearchKey searchKey = new SearchKey();
        Resume searchResume = new Resume(uuid);
        searchKey.setIndex(Arrays.binarySearch(storage, 0, size, searchResume));
        return searchKey;
    }
}