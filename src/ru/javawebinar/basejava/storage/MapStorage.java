package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SearchKey;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<String, Resume>();

    @Override
    protected SearchKey getSearchKey(String uuid) {
        SearchKey searchKey = new SearchKey();
        try {
            searchKey.setUuid(storage.get(uuid).getUuid());
        } catch (NullPointerException e) {
            searchKey.setUuid("");
        }

        return searchKey;
    }

    @Override
    protected Resume getResume(SearchKey searchKey) {
        return storage.get(searchKey.getUuid());
    }

    @Override
    protected void replaceResume(Resume resume, SearchKey searchKey) {
        storage.put(searchKey.getUuid(), resume);
    }

    @Override
    protected void insertResume(Resume resume, SearchKey searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteResume(SearchKey searchKey) {
        storage.remove(searchKey.getUuid());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] result = storage.values().toArray(new Resume[0]);
        return result;
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected boolean verifySearchKey(SearchKey searchKey) {
        if (searchKey.getUuid().equals("")) {
            return false;
        }
        return true;
    }
}