package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlTransactionExecutor<T> {
    T execute(Connection conn) throws SQLException;
}
