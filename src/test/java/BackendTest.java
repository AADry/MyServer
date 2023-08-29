import dao.impl.ConnectionManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class BackendTest {
    @Rule
    public PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("test-db")
            .withUsername("test")
            .withPassword("test");

    @Test
    public void setUp() throws SQLException {
        Connection connection = getConnection();
        connection.close();
        /*url = "jdbc:postgresql://localhost/test-db?user=test&password=test&ssl=true";
        conn = DriverManager.getConnection(url);*/
    }
    @Test
    public void insert() throws SQLException {
        Connection connection = getConnection();
        connection.close();

    }
    private Connection getConnection() throws SQLException {
        String url = postgreSQLContainer.getJdbcUrl();
        String user = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();
        ConnectionManager.setProperties(url,user,password);
        return ConnectionManager.getConnection();
    }
    @Test
    public void createTable(){

    }
}
