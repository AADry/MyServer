package dao.impl;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static BasicDataSource ds = new BasicDataSource();


    static {
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    public static void setProperties(String url, String username, String password){
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
    }

    private ConnectionManager(){ }
}
