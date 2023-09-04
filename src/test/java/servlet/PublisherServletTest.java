package servlet;

import dao.impl.BookDao;
import dao.impl.PublisherDao;
import dbconnection.ConnectionManager;
import exception.DaoException;
import model.Publisher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;

public class PublisherServletTest {
    @Rule
    public JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withInitScript("init.sql")
            .withDatabaseName("small")
            .withUsername("admin")
            .withPassword("admin");
    PublisherServlet publisherServlet = new PublisherServlet();

    @Before
    public void setUp() {
        Properties properties = new Properties();
        properties.setProperty("url", postgreSQLContainer.getJdbcUrl());
        properties.setProperty("username", postgreSQLContainer.getUsername());
        properties.setProperty("password", postgreSQLContainer.getPassword());
        ConnectionManager.setProperties(properties);
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'get' ")
    public void doGetTest() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, DaoException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        PublisherDao publisherDao = mock(PublisherDao.class);
        publisherServlet.publisherDao = publisherDao;
        publisherServlet.doGet(request, response);
        verify(publisherDao, atLeast(1)).get(1);

        verify(request, atLeast(1)).getParameter("id");
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'save' ")
    public void doPostTest() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, DaoException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("2"); //put unique id here!
        when(request.getParameter("name")).thenReturn("testName");
        when(request.getParameter("address")).thenReturn("testAddress");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        PublisherDao publisherDao = mock(PublisherDao.class);
        publisherServlet.publisherDao = publisherDao;
        publisherServlet.doPost(request, response);
        verify(publisherDao, atLeast(1)).save(isA(Publisher.class));

        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("name");
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'update' ")
    public void doPut() throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException, DaoException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("testName");
        when(request.getParameter("address")).thenReturn("testAddress");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        PublisherDao publisherDao = mock(PublisherDao.class);
        publisherServlet.publisherDao = publisherDao;
        publisherServlet.doPut(request, response);
        verify(publisherDao, atLeast(1)).update(isA(Publisher.class));

        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("name");
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'delete' ")
    public void doDelete() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, DaoException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("2");
        when(request.getParameter("name")).thenReturn("testName");
        when(request.getParameter("address")).thenReturn("testAddress");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        PublisherDao publisherDao = mock(PublisherDao.class);
        publisherServlet.publisherDao = publisherDao;
        publisherServlet.doDelete(request, response);
        verify(publisherDao, atLeast(1)).delete(isA(Publisher.class));

        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("name");
    }
}
