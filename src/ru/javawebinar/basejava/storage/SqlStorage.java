package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

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
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "  SELECT * FROM resume r " +
                    "  WHERE r.uuid =? ")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "  SELECT * FROM contact c " +
                    "  WHERE c.resume_uuid =? ")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    getContactFromDb(rs, resume);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "  SELECT * FROM section s " +
                    "  WHERE s.resume_uuid =? ")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    getSectionFromDb(rs, resume);
                }
            }
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

            try (PreparedStatement ps = conn.prepareStatement("" +
                    " DELETE FROM contact c WHERE c.resume_uuid = ?; ")) {
                ps.setString(1, resume.getUuid());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement("" +
                    " DELETE FROM section s WHERE s.resume_uuid = ? ")) {
                ps.setString(1, resume.getUuid());
                ps.executeUpdate();
            }

            putContactsToDb(conn, resume);
            putSectionsToDb(conn, resume);
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
            putSectionsToDb(conn, resume);
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
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> allSorted = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "  SELECT * FROM resume r " +
                    "  ORDER BY r.full_name,r.uuid ")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    allSorted.put(uuid, resume);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(" SELECT * FROM contact ")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    getContactFromDb(rs, allSorted.get(uuid));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(" SELECT * FROM section ")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    getSectionFromDb(rs, allSorted.get(uuid));
                }
            }
            return new ArrayList<>(allSorted.values());
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

    private void getSectionFromDb(ResultSet rs, Resume resume) throws SQLException {
        SectionType type = SectionType.valueOf(rs.getString("type"));
        String value = rs.getString("value");
        if (value != null) {
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    resume.addSection(type, new TextSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    resume.addSection(type, new ListSection(Arrays.asList(value.split("\n"))));
                    break;
            }
        }
    }

    private void putSectionsToDb(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO section (value, type, resume_uuid) VALUES (?,?,?) ON CONFLICT DO NOTHING")) {
            for (Map.Entry<SectionType, AbstractSection> e : resume.getSections().entrySet()) {
                AbstractSection abstractSection = e.getValue();
                String stringValue = "";

                switch (e.getKey()) {
                    case OBJECTIVE:
                    case PERSONAL:
                        stringValue = ((TextSection) abstractSection).getContent();
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        stringValue = String.join("\n", ((ListSection) abstractSection).getContent());
                        break;
                }

                ps.setString(1, stringValue);
                ps.setString(2, e.getKey().name());
                ps.setString(3, resume.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
