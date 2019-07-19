package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
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
        return sqlHelper.execute("" +
                        "  SELECT * FROM resume r " +
                        "    LEFT JOIN contact c " +
                        "    ON r.uuid = c.resume_uuid " +
                        "  WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        getContactFromDb(rs, resume);
                    } while (rs.next());

                    return resume;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE resume r SET uuid = ?, full_name = ? WHERE r.uuid = ?")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.setString(3, resume.getUuid());
                int sqlResult = ps.executeUpdate();
                if (sqlResult == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM contact c WHERE c.resume_uuid = ?")) {
                ps.setString(1, resume.getUuid());
                int sqlResult = ps.executeUpdate();
                if (sqlResult == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }

            putContactsToDb(conn, resume);

            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO resume (uuid, full_name) VALUES (?,?) ON CONFLICT(uuid) DO NOTHING")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                int sqlResult = ps.executeUpdate();
                if (sqlResult == 0) {
                    throw new ExistStorageException(resume.getUuid());
                }
            }

            putContactsToDb(conn, resume);

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
        return sqlHelper.execute("" +
                        "  SELECT * FROM resume r " +
                        "    LEFT JOIN contact c " +
                        "    ON r.uuid = c.resume_uuid " +
                        "  ORDER BY r.full_name,r.uuid ",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    List<Resume> allSorted = new ArrayList<>();
                    String prevUuid = "";
                    while (rs.next()) {
                        // New resume
                        if (!prevUuid.equals(rs.getString("uuid"))) {
                            Resume resume = new Resume(
                                    rs.getString("uuid"),
                                    rs.getString("full_name"));
                            getContactFromDb(rs, resume);
                            allSorted.add(resume);
                        }
                        // Add contacts to existing resume
                        else {
                            getContactFromDb(rs, allSorted.get(allSorted.size() - 1));
                        }
                        prevUuid = rs.getString("uuid");
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

    private void getContactFromDb(ResultSet rs, Resume resume) throws SQLException {
        String type = rs.getString("type");
        String value = rs.getString("value");
        if (type != null && value != null) {
            resume.addContact(ContactType.valueOf(type), value);
        }
    }

    private void putContactsToDb(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO contact (value, type, resume_uuid) VALUES (?,?,?) ON CONFLICT DO NOTHING")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, e.getValue());
                ps.setString(2, e.getKey().name());
                ps.setString(3, resume.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
