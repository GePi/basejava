package utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlStatementExecutor<T> {
    T execute(PreparedStatement statement) throws SQLException;
}
