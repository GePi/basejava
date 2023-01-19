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
            updateBasis(conn, r);
            updateContracts(conn, r);
            return null;
        });
    }

    public void save(Resume r) {
        sqlHelper.transactionalExecute((conn) -> {
            insertBasis(conn, r);
            insertContacts(conn, r);
            return null;
        });
    }

    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute((conn) -> {
            Resume r = selectBasis(conn, uuid);
            selectContact(conn, r);
            return r;
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
        return sqlHelper.transactionalExecute((conn) -> {
            List<Resume> resumes = new ArrayList<>();
            try (PreparedStatement st = conn.prepareStatement("SELECT uuid, full_name FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
            }
            for (var r : resumes) {
                selectContact(conn, r);
            }
            return resumes;
        });
    }

    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", (statement) -> {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }

    private void updateContracts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("UPDATE contact SET value = ? WHERE  type = ? AND resume_uuid = ?")) {
            fillParams(st, r, new String[]{"contact.value", "contact.type", "contact.resume_uuid"});
            st.executeBatch();
        }
    }

    private void updateBasis(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE  uuid = ?")) {
            fillParams(st, r, new String[]{"resume.full_name", "resume.uuid"});
            if (st.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
        }
    }

    private void insertBasis(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO resume VALUES (?,?)")) {
            fillParams(st, r, new String[]{"resume.uuid", "resume.full_name"});
            st.executeUpdate();
        }
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO contact VALUES (DEFAULT,?,?,?)")) {
            fillParams(st, r, new String[]{"contact.type", "contact.value", "contact.resume_uuid"});
            st.executeBatch();
        }
    }

    private Resume selectBasis(Connection conn, String uuid) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("SELECT uuid, full_name FROM resume WHERE  uuid =?")) {
            st.setString(1, uuid);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(rs.getString("uuid"), rs.getString("full_name"));
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

    private void fillParams(PreparedStatement st, Resume r, String[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            switch (params[i]) {
                case "resume.uuid" -> st.setString(i + 1, r.getUuid());
                case "resume.full_name" -> st.setString(i + 1, r.getFullName());
            }
        }
        for (var contact : r.getContacts().entrySet()) {
            for (int i = 0; i < params.length; i++) {
                switch (params[i]) {
                    case "contact.type" -> st.setString(i + 1, contact.getKey().name());
                    case "contact.value" -> st.setString(i + 1, contact.getValue());
                    case "contact.resume_uuid" -> st.setString(i + 1, r.getUuid());
                }
            }
            st.addBatch();
        }
    }
}
