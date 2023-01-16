package utils;

import exceptions.StorageException;
import sql.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLHelper {
    public static final String POSTGRES_UNIQUE_VIOLATION = "23505";

    public static int exec(ConnectionFactory cf, String sql, SqlParameterSetter sqlSetter) throws SQLException {
        try (var conn = cf.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            if (sqlSetter != null) {
                sqlSetter.set(statement);
            }
            return statement.executeUpdate();
        }
    }

    public static void exec(ConnectionFactory cf, String sql) throws SQLException {
        exec(cf, sql, null);
    }

    public static <T> T fetch(ConnectionFactory cf, String sql, SqlParameterSetter sqlSetter, SqlSupplier<T> supplier) {
        try (var conn = cf.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            if (sqlSetter != null) {
                sqlSetter.set(statement);
            }
            ResultSet resultSet = statement.executeQuery();
            return supplier.supply(resultSet);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
