package dbconnection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
    public static void setProperties(Properties properties){
        connectionUrl = properties.getProperty("url");
        dbUser = properties.getProperty("username");
        dbPwd = properties.getProperty("password");
    }
    private ConnectionManager() {
    }
}
