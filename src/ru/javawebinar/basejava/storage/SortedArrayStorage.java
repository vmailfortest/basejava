package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
/*
    private static class ResumeComparator implements Comparator<Resume> {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    }
*/

    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    private static final Comparator<Resume> FULLNAME_COMPARATOR = (o1, o2) -> {
        int result = 0;
        if (o1.getFullName() != null && o2.getFullName() != null) {
            result = o1.getFullName().compareTo(o2.getFullName());
        }
        if (result == 0) {
            result = o1.getUuid().compareTo(o2.getUuid());
        }
        return result;
    };


    @Override
    protected void insertElement(Resume resume, int searchKey) {
        searchKey = -searchKey - 1;
        System.arraycopy(storage, searchKey, storage, searchKey + 1, size - searchKey);
        storage[searchKey] = resume;
    }

    @Override
    protected void deleteElement(int searchKey) {
        System.arraycopy(storage, searchKey + 1, storage, searchKey, size - searchKey - 1);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchResume = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchResume, FULLNAME_COMPARATOR);
    }
}