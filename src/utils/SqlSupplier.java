package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SqlSupplier<T> {
    T supply(ResultSet rs) throws SQLException;
}
