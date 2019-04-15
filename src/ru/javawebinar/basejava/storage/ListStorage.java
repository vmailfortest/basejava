package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SearchKey;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<Resume>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] result = storage.toArray(new Resume[0]);
        return result;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getResume(SearchKey searchKey) {
        return storage.get(searchKey.getIndex());
    }

    @Override
    protected void replaceResume(Resume resume, SearchKey searchKey) {
        storage.set(searchKey.getIndex(), resume);
    }

    @Override
    protected SearchKey getSearchKey(String uuid) {
        SearchKey searchKey = new SearchKey();
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                searchKey.setIndex(i);
                break;
            }
        }
        return searchKey;
    }

    protected boolean verifySearchKey(SearchKey searchKey) {
        if (searchKey.getIndex() < 0) {
            return false;
        }
        return true;
    }

    @Override
    protected void insertResume(Resume resume, SearchKey searchKey) {
        storage.add(resume);
    }

    @Override
    protected void deleteResume(SearchKey searchKey) {
        storage.remove(searchKey.getIndex());
    }
}
