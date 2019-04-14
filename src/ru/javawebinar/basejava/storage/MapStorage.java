package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<Integer, Resume> storage = new HashMap<Integer, Resume>();
    private Map<String, Integer> uuidToKey = new HashMap<String, Integer>();
    private int key = 0;

    @Override
    protected int getIndex(String uuid) {
        Integer result = uuidToKey.get(uuid);
        if (result == null) {
            return -1;
        }
        return result;
    }

    @Override
    protected Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    protected void replaceResume(Resume resume, int index) {
        storage.put(index, resume);
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        storage.put(key, resume);
        uuidToKey.put(resume.getUuid(), key);
        key++;
    }

    @Override
    protected void deleteResume(int index) {
        uuidToKey.remove(storage.get(index).getUuid());
        storage.remove(index);
    }

    @Override
    public void clear() {
        storage.clear();
        uuidToKey.clear();
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
