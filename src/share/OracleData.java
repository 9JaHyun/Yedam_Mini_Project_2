package share;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleData {
    public static OracleData INSTANCE = null;
    private String jdbc_driver = "oracle.jdbc.driver.OracleDriver";
    private String jdbc_url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String username = "project1";
    private String password = "project1";

    private OracleData() {}

    public static OracleData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OracleData();
        }
        return INSTANCE;
    }

    public Connection connect() {
        Connection conn = null;
        try {
            Class.forName(jdbc_driver);
            conn = DriverManager.getConnection(jdbc_url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
        }
        return conn;
    }


}
