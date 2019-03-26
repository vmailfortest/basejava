package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    public void update(Resume resume) {
        int resumeIndex = getIndex(resume.getUuid());
        if (resumeIndex < 0) {
            System.out.println("ERROR: Resume for update not found!");
        }
        storage[resumeIndex] = resume;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected Boolean verifyBeforeSave(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("ERROR: Resume storage is full!");
            return false;
        }

        int resumeIndex = getIndex(resume.getUuid());
        if (resumeIndex >= 0) {
            System.out.println("ERROR: Resume is already exists!");
            return false;
        }
        return true;
    }

}
