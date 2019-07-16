package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume",
                ps -> {
                    ps.execute();
                    return null;
                });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.execute("UPDATE resume r SET uuid = ?, full_name = ? WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    ps.setString(3, resume.getUuid());
                    int sqlResult = ps.executeUpdate();
                    if (sqlResult == 0) {
                        throw new NotExistStorageException(resume.getUuid());
                    }
                    return null;
                });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?,?) ON CONFLICT(uuid) DO NOTHING",
                ps -> {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    int sqlResult = ps.executeUpdate();
                    if (sqlResult == 0) {
                        throw new ExistStorageException(resume.getUuid());
                    }
                    return null;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid=?",
                ps -> {
                    ps.setString(1, uuid);
                    int sqlResult = ps.executeUpdate();
                    if (sqlResult == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume r ORDER BY r.full_name",
                ps -> {
            ResultSet rs = ps.executeQuery();
                    List<Resume> allSorted = new ArrayList<>();
            while (rs.next()) {
                allSorted.add(new Resume(
                        rs.getString("uuid").trim(),
                        rs.getString("full_name")));
            }
                    return allSorted;
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT (*) AS total FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    return rs.getInt("total");
                });
    }
}
