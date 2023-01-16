package storage;

import exceptions.ExistStorageException;
import exceptions.NotExistStorageException;
import exceptions.StorageException;
import model.Resume;
import sql.ConnectionFactory;
import utils.SQLHelper;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void clear() {
        try {
            SQLHelper.exec(connectionFactory, "DELETE FROM resume");
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public void update(Resume r) {
        try {
            int updNum = SQLHelper.exec(connectionFactory, "UPDATE resume SET full_name = ? WHERE uuid = ?", (statement) -> {
                statement.setString(1, r.getFullName());
                statement.setString(2, r.getUuid());
            });
            if (updNum == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public void save(Resume r) {
        try {
            SQLHelper.exec(connectionFactory, "INSERT INTO resume VALUES (?,?)", (statement) -> {
                statement.setString(1, r.getUuid());
                statement.setString(2, r.getFullName());
            });
        } catch (SQLException e) {
            if (SQLHelper.POSTGRES_UNIQUE_VIOLATION.equals(e.getSQLState())) {
                throw new ExistStorageException(r.getUuid());
            }
            throw new StorageException(e);
        }
    }

    public Resume get(String uuid) {
        return SQLHelper.fetch(connectionFactory, "SELECT * FROM resume WHERE uuid = ?", (statement) -> statement.setString(1, uuid), (resultSet) -> {
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name"));
        });
    }

    public void delete(String uuid) {
        try {
            int updNum = SQLHelper.exec(connectionFactory, "DELETE FROM resume WHERE uuid = ?", (statement) -> statement.setString(1, uuid));
            if (updNum == 0) {
                throw new NotExistStorageException(uuid);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public List<Resume> getAllSorted() {
        return SQLHelper.fetch(connectionFactory, "SELECT * FROM resume", null, (resultSet) -> {
            List<Resume> resumeList = new ArrayList<>(resultSet.getFetchSize());
            while (resultSet.next()) {
                resumeList.add(new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name")));
            }
            resumeList.sort(Resume.COMPARE_BY_NAME);
            return resumeList;
        });
    }

    public int size() {
        return SQLHelper.fetch(connectionFactory, "SELECT COUNT(*) as numbers_resume FROM resume", null, (resultSet) -> {
            resultSet.next();
            return resultSet.getInt("numbers_resume");
        });
    }
}
