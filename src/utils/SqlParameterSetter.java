package utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlParameterSetter {
    void set( PreparedStatement statement ) throws SQLException;
}
