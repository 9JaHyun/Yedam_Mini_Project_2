package share;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementMaker {
    PreparedStatement createPreparedStatement(Connection con) throws SQLException;
}
