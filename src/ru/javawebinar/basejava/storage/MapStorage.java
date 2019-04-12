package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<String, Resume>();

    @Override
    protected int getIndex(String uuid) {
        if (storage.get(uuid) == null) {
            return -1;
        }
        return 1;
    }

    @Override
    protected Resume getResume(String uuid, int index) {
        return storage.get(uuid);
    }

    @Override
    protected void replaceResume(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteResume(String uuid, int index) {
        storage.remove(uuid);
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
}
