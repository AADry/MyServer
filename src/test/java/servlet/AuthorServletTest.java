package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.impl.AuthorDao;
import dao.impl.PublisherDao;
import dbconnection.ConnectionManager;
import exception.DaoException;
import exception.ServiceException;
import model.Author;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import service.AuthorService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Properties;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;

public class AuthorServletTest {
    @Rule
    public JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withInitScript("init.sql")
            .withDatabaseName("small")
            .withUsername("admin")
            .withPassword("admin");
    AuthorServlet authorServlet = new AuthorServlet();
    private static final Gson GSON = new GsonBuilder().create();

    @Before
    public void setUp() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("dbproperties.cfg");
        Properties properties = new Properties();
        properties.load(new FileReader(url.getPath()));
        properties.setProperty("url", postgreSQLContainer.getJdbcUrl());
        ConnectionManager.setProperties(properties);
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'get' ")
    public void doGetTest() throws IOException, ServiceException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));
        AuthorService authorService = mock(AuthorService.class);
        when(authorService.handleGetRequest(request, response)).thenReturn(GSON.toJson(new Author(1, "name")));
        authorServlet.authorService = authorService;
        authorServlet.doGet(request, response);
        verify(authorService, atLeast(1)).handleGetRequest(request, response);
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'save' ")
    public void doPostTest() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ServletException, DaoException, ServiceException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("2"); //put unique id here!
        when(request.getParameter("name")).thenReturn("testName");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));
        AuthorService authorService = mock(AuthorService.class);
        authorServlet.authorService = authorService;
        authorServlet.doPost(request, response);
        verify(authorService, atLeast(1)).handlePostRequest(request, response);
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'update' ")
    public void doPut() throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException, DaoException, ServiceException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("updtestName");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        AuthorService authorService = mock(AuthorService.class);
        authorServlet.authorService = authorService;
        authorServlet.doPut(request, response);
        verify(authorService, atLeast(1)).handlePutRequest(request, response);
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'delete' ")
    public void doDelete() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, DaoException, ServiceException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("2");
        when(request.getParameter("title")).thenReturn("testTitle");
        when(request.getParameter("publisher")).thenReturn("1");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));
        AuthorService authorService = mock(AuthorService.class);
        authorServlet.authorService = authorService;
        authorServlet.doDelete(request, response);
        verify(authorService, atLeast(1)).handleDeleteRequest(request, response);
    }
}
