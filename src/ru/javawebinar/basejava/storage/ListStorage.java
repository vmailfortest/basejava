package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

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
    protected Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    protected void replaceResume(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected void deleteResume(int index) {
        storage.remove(index);
    }
}
