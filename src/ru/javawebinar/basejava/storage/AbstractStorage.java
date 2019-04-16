package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public Resume get(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!verifySearchKey(searchKey)) {
            throw new NotExistStorageException(uuid);
        }

        return getResume(searchKey);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (verifySearchKey(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }

        insertResume(resume, searchKey);
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (!verifySearchKey(searchKey)) {
            throw new NotExistStorageException(resume.getUuid());
        }

        replaceResume(resume, searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!verifySearchKey(searchKey)) {
            throw new NotExistStorageException(uuid);
        }

        deleteResume(searchKey);
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void replaceResume(Resume resume, Object searchKey);

    protected abstract void insertResume(Resume resume, Object searchKey);

    protected abstract void deleteResume(Object searchKey);

    protected abstract boolean verifySearchKey(Object searchKey);
}
