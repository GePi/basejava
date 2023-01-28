package storage;

import exceptions.NotExistStorageException;
import model.*;
import sql.SQLHelper;

import java.sql.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final static String SECTION_LINES_DELIMITER = "\n";
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
            deleteContact(conn, r);
            deleteSection(conn, r);
            insertContact(conn, r);
            insertSection(conn, r);
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
            insertSection(conn, r);
            return null;
        });
    }

    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute((conn) -> {
            Resume r;
            try (PreparedStatement st = conn.prepareStatement("SELECT uuid, full_name FROM resume WHERE uuid =?")) {
                st.setString(1, uuid);
                ResultSet rs = st.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(rs.getString("uuid"), rs.getString("full_name"));
            }

            try (PreparedStatement st = conn.prepareStatement("SELECT type, value  FROM contact WHERE resume_uuid =?")) {
                st.setString(1, r.getUuid());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    r.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                }
            }

            try (PreparedStatement st = conn.prepareStatement("SELECT type, value  FROM section WHERE resume_uuid =?")) {
                st.setString(1, r.getUuid());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    addSection(rs, r);
                }
            }
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
            Map<String, Resume> resumeMap = new LinkedHashMap<>();

            try (PreparedStatement st = conn.prepareStatement("SELECT uuid, full_name FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    resumeMap.put(rs.getString("uuid"), new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
            }

            try (PreparedStatement st = conn.prepareStatement("SELECT type, value, resume_uuid FROM contact")) {
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    resumeMap.get(rs.getString("resume_uuid")).addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                }
            }

            try (PreparedStatement st = conn.prepareStatement("SELECT type, value, resume_uuid FROM section")) {
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    addSection(rs, resumeMap.get(rs.getString("resume_uuid")));
                }
            }
            return resumeMap.values().stream().toList();
        });
    }

    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", (statement) -> {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }

    private static void deleteContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid =?")) {
            st.setString(1, r.getUuid());
            st.executeUpdate();
        }
    }

    private static void deleteSection(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("DELETE FROM section WHERE resume_uuid =?")) {
            st.setString(1, r.getUuid());
            st.executeUpdate();
        }
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

    private void insertSection(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO section VALUES (DEFAULT,?,?,?)")) {
            for (var section : r.getSections().entrySet()) {
                String sectionAsString = switch (section.getKey()) {
                    case ACHIEVEMENT, QUALIFICATION ->
                            String.join(SECTION_LINES_DELIMITER, ((ListSection) section.getValue()).getLines());
                    case OBJECTIVE, PERSONAL -> ((TextSection) section.getValue()).getText();
                    default -> null;
                };
                if (sectionAsString != null) {
                    st.setString(1, section.getKey().name());
                    st.setString(2, sectionAsString);
                    st.setString(3, r.getUuid());
                    st.addBatch();
                }
            }
            st.executeBatch();
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        var sectionType = SectionType.valueOf(rs.getString("type"));
        switch (sectionType) {
            case ACHIEVEMENT, QUALIFICATION ->
                    r.addSection(sectionType, new ListSection(Arrays.stream(rs.getString("value").split(SECTION_LINES_DELIMITER)).toList()));
            case OBJECTIVE, PERSONAL -> r.addSection(sectionType, new TextSection(rs.getString("value")));
        }
    }
}
