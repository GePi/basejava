package sql;

import exceptions.StorageException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHelper {

    private final ConnectionFactory connectionFactory;

    public SQLHelper(ConnectionFactory connectionFactory) {
        // In tomcat the DB does not work without this
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String sql, SqlStatementExecutor<T> executor) {
        try (var conn = connectionFactory.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            return executor.execute(statement);
        } catch (SQLException e) {
            throw ExeptionUtil.convertException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransactionExecutor<T> executor) {
        try (var conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExeptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
