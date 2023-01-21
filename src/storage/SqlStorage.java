package storage;

import exceptions.NotExistStorageException;
import model.ContactType;
import model.Resume;
import sql.SQLHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SQLHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SQLHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::executeUpdate);
    }

    public void update(Resume r) {
        sqlHelper.transactionalExecute((conn) -> {

            try (PreparedStatement st = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE  uuid =?")) {
                st.setString(1, r.getFullName());
                st.setString(2, r.getUuid());
                if (st.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }

            try (PreparedStatement st = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid =?")) {
                st.setString(1, r.getUuid());
                st.executeUpdate();
            }

            insertContact(conn, r);
            return null;
        });
    }

    public void save(Resume r) {
        sqlHelper.transactionalExecute((conn) -> {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO resume VALUES (?,?)")) {
                st.setString(1, r.getUuid());
                st.setString(2, r.getFullName());
                st.executeUpdate();
            }
            insertContact(conn, r);
            return null;
        });
    }

    public Resume get(String uuid) {
        return sqlHelper.execute(
                "SELECT r.uuid, r.full_name, c.type, c.value " +
                        "FROM resume r " +
                        "LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                        "WHERE uuid = ?", (statement) -> {
                    statement.setString(1, uuid);
                    ResultSet rs = statement.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                    do {
                        if (rs.getString("type") != null) {
                            resume.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                        }
                    } while (rs.next());
                    return resume;
                });
    }

    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", (statement) -> {
            statement.setString(1, uuid);
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    public List<Resume> getAllSorted() {
        return sqlHelper.execute(
                "SELECT r.uuid, r.full_name, c.type, c.value " +
                        "FROM resume r " +
                        "LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                        "ORDER BY full_name, uuid", (statement) -> {
                    ResultSet rs = statement.executeQuery();
                    List<Resume> resumeList = new ArrayList<>();
                    Resume resume = null;
                    while (rs.next()) {
                        if (resume == null || !resume.getUuid().equals(rs.getString("uuid"))) {
                            resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                            resumeList.add(resume);
                        }
                        if (rs.getString("type") != null) {
                            resume.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                        }
                    }
                    return resumeList;
                });
    }

    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", (statement) -> {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }

    private void insertContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO contact VALUES (DEFAULT,?,?,?)")) {
            for (var contact : r.getContacts().entrySet()) {
                st.setString(1, contact.getKey().name());
                st.setString(2, contact.getValue());
                st.setString(3, r.getUuid());
                st.addBatch();
            }
            st.executeBatch();
        }
    }

    private void selectContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("SELECT type, value  FROM contact WHERE  resume_uuid =?")) {
            st.setString(1, r.getUuid());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                r.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
            }
        }
    }
}
