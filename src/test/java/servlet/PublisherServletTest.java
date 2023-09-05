package servlet;

import dao.impl.BookDao;
import dao.impl.PublisherDao;
import dbconnection.ConnectionManager;
import exception.DaoException;
import exception.ServiceException;
import model.Publisher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import service.PublisherService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
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
        when(request.getParameter("name")).thenReturn("testPublisher");
        when(request.getParameter("address")).thenReturn("testAddress");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        PublisherService publisherService = mock(PublisherService.class);
        publisherServlet.publisherService = publisherService;
        publisherServlet.doGet(request, response);
        verify(publisherService, atLeast(1)).handleGetRequest(request,response);
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'save' ")
    public void doPostTest() throws IOException,  ServiceException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("2"); //put unique id here!
        when(request.getParameter("name")).thenReturn("testName");
        when(request.getParameter("address")).thenReturn("testAddress");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        PublisherService publisherService = mock(PublisherService.class);
        publisherServlet.publisherService = publisherService;
        publisherServlet.doPost(request, response);
        verify(publisherService, atLeast(1)).handlePostRequest(request,response);
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'update' ")
    public void doPut() throws  IOException, ServiceException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("testName");
        when(request.getParameter("address")).thenReturn("testAddress");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        PublisherService publisherService = mock(PublisherService.class);
        publisherServlet.publisherService = publisherService;
        publisherServlet.doPut(request, response);
        verify(publisherService, atLeast(1)).handlePutRequest(request,response);
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'delete' ")
    public void doDelete() throws  IOException,  ServiceException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("2");
        when(request.getParameter("name")).thenReturn("testName");
        when(request.getParameter("address")).thenReturn("testAddress");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        PublisherService publisherService = mock(PublisherService.class);
        publisherServlet.publisherService = publisherService;
        publisherServlet.doDelete(request, response);
        verify(publisherService, atLeast(1)).handleDeleteRequest(request,response);
    }
}
