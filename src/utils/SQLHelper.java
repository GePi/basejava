package utils;

import exceptions.ExistStorageException;
import exceptions.StorageException;
import sql.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHelper {

    public static final String POSTGRES_UNIQUE_VIOLATION = "23505";
    private final ConnectionFactory connectionFactory;

    public SQLHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String sql, SqlStatementExecutor<T> supplier) {
        try (var conn = connectionFactory.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            return supplier.execute(statement);
        } catch (SQLException e) {
            if (SQLHelper.POSTGRES_UNIQUE_VIOLATION.equals(e.getSQLState())) {
                throw new ExistStorageException(e);
            }
            throw new StorageException(e);
        }
    }
}
