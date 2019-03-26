package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        if (!verifyBeforeSave(resume)) {
            return;
        }

        if (size == 0) {
            storage[0] = resume;
            size++;
            return;
        }
        if (resume.compareTo(storage[size - 1]) > 0) {
            storage[size] = resume;
            size++;
            return;
        }

        for (int i = 0; i < size; i++) {
            if (resume.compareTo(storage[i]) < 0) {
                System.arraycopy(storage, i, storage, i + 1, size - i);
                storage[i] = resume;
                size++;
                return;
            }
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR: Resume for delete not found!");
            return;
        }

        System.arraycopy(storage, index + 1, storage, index, size - index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}