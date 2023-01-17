package storage;

import exceptions.ExistStorageException;
import exceptions.NotExistStorageException;
import exceptions.StorageException;
import model.Resume;
import utils.SQLHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        if (sqlHelper.execute("UPDATE resume SET full_name = ? WHERE uuid = ?", (statement) -> {
            statement.setString(1, r.getFullName());
            statement.setString(2, r.getUuid());
            return statement.executeUpdate();
        }) == 0) {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    public void save(Resume r) {
        try {
            sqlHelper.execute("INSERT INTO resume VALUES (?,?)", (statement) -> {
                statement.setString(1, r.getUuid());
                statement.setString(2, r.getFullName());
                return statement.executeUpdate();
            });
        } catch (StorageException storageException) {
            SQLException sqlException = (SQLException) storageException.getCause();
            if (SQLHelper.POSTGRES_UNIQUE_VIOLATION.equals(sqlException.getSQLState())) {
                throw new ExistStorageException(r.getUuid());
            }
            throw storageException;
        }
    }

    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume WHERE uuid = ?", (statement) -> {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(resultSet.getString("uuid"), resultSet.getString("full_name"));
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
        return sqlHelper.execute("SELECT * FROM resume", (statement) -> {
            ResultSet resultSet = statement.executeQuery();
            List<Resume> resumeList = new ArrayList<>(resultSet.getFetchSize());
            while (resultSet.next()) {
                resumeList.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
            }
            resumeList.sort(Resume.COMPARE_BY_NAME);
            return resumeList;
        });
    }

    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) as numbers_resume FROM resume", (statement) -> {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("numbers_resume");
        });
    }
}
