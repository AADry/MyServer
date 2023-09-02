package dbconnection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    static String connectionUrl = "jdbc:postgresql:smalldb";
    static String dbUser = "postgres";
    static String dbPwd = "admin";

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
        return conn;
    }

    public static void setProperties(String url, String username, String password) {
        connectionUrl = url;
        dbUser = username;
        dbPwd = password;
    }

    private ConnectionManager() {
    }
}
