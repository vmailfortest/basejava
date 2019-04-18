package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private Map<Resume, Resume> storage = new HashMap<>();

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put(new Resume(resume.getUuid()), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get(new Resume((String) searchKey));
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage.put(new Resume((String) searchKey), resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(new Resume((String) searchKey));
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(storage.values());
        Collections.sort(list);
        return new ArrayList<>(storage.values());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected String getSearchKey(String uuid) {
        try {
            return storage.get(new Resume(uuid)).getUuid();
        } catch (NullPointerException e) {
            return uuid;
        }
    }

    protected boolean isExist(Object searchKey) {
        return storage.containsKey(new Resume((String) searchKey));
    }
}
