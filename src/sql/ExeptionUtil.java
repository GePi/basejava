package sql;

import exceptions.ExistStorageException;
import exceptions.StorageException;

import java.sql.SQLException;

public class ExeptionUtil {
    public static final String POSTGRES_UNIQUE_VIOLATION = "23505";

    public static StorageException convertException(SQLException e) {
        if (POSTGRES_UNIQUE_VIOLATION.equals(e.getSQLState())) {
            throw new ExistStorageException(e);
        }
        throw new StorageException(e);
    }
}
