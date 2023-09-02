package servlet;

import dbconnection.ConnectionManager;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;

public class AuthorServletTest {
    @Rule
    public JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withInitScript("init.sql")
            .withDatabaseName("small")
            .withUsername("admin")
            .withPassword("admin");

    @Test
    public void doGetTest() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ConnectionManager.setProperties(postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getUsername());
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        Method doGet = AuthorServlet.class.getDeclaredMethod("doGet", HttpServletRequest.class, HttpServletResponse.class);
        doGet.setAccessible(true);
        doGet.invoke(new AuthorServlet(), request, response);

        verify(request, atLeast(1)).getParameter("id");
        assertNotNull(response);
    }

    @Test
    public void doPostTest() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ConnectionManager.setProperties(postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getUsername());
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("2"); //put unique id here!
        when(request.getParameter("name")).thenReturn("testName");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        Method doGet = AuthorServlet.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        doGet.setAccessible(true);
        doGet.invoke(new AuthorServlet(), request, response);

        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("name");
        assertNotNull(response);
    }
    @Test
    public void doPut() throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException {
        ConnectionManager.setProperties(postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getUsername());
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("title")).thenReturn("updtestName");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        Method doGet = AuthorServlet.class.getDeclaredMethod("doPut", HttpServletRequest.class, HttpServletResponse.class);
        doGet.setAccessible(true);
        doGet.invoke(new AuthorServlet(), request, response);

        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("name");
        assertNotNull(response);
    }
    @Test
    public void doDelete() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        ConnectionManager.setProperties(postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getUsername());
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("2");
        when(request.getParameter("title")).thenReturn("testTitle");
        when(request.getParameter("publisher")).thenReturn("1");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        Method doGet = AuthorServlet.class.getDeclaredMethod("doDelete", HttpServletRequest.class, HttpServletResponse.class);
        doGet.setAccessible(true);
        doGet.invoke(new AuthorServlet(), request, response);

        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("name");
        assertNotNull(response);
    }
}
