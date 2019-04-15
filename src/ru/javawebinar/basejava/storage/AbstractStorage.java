package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SearchKey;

public abstract class AbstractStorage implements Storage {
    @Override
    public Resume get(String uuid) {
        SearchKey searchKey = getSearchKey(uuid);
        if (!verifySearchKey(searchKey)) {
            throw new NotExistStorageException(uuid);
        }

        return getResume(searchKey);
    }

    @Override
    public void save(Resume resume) {
        SearchKey searchKey = getSearchKey(resume.getUuid());
        if (verifySearchKey(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }

        insertResume(resume, searchKey);
    }

    @Override
    public void update(Resume resume) {
        SearchKey searchKey = getSearchKey(resume.getUuid());
        if (!verifySearchKey(searchKey)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        replaceResume(resume, searchKey);
    }

    @Override
    public void delete(String uuid) {
        SearchKey searchKey = getSearchKey(uuid);
        if (!verifySearchKey(searchKey)) {
            throw new NotExistStorageException(uuid);
        }

        deleteResume(searchKey);
    }

    protected abstract SearchKey getSearchKey(String uuid);

    protected abstract Resume getResume(SearchKey searchKey);

    protected abstract void replaceResume(Resume resume, SearchKey searchKey);

    protected abstract void insertResume(Resume resume, SearchKey searchKey);

    protected abstract void deleteResume(SearchKey searchKey);

    protected abstract boolean verifySearchKey(SearchKey searchKey);
}
