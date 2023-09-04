package servlet;

import dao.impl.AuthorDao;
import dao.impl.BookDao;
import dbconnection.ConnectionManager;
import exception.DaoException;
import model.Author;
import model.Book;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import static junit.framework.TestCase.assertNotNull;

public class BookServletTest extends Mockito {
    @Rule
    public JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withInitScript("init.sql")
            .withDatabaseName("small")
            .withUsername("admin")
            .withPassword("admin");
    BookServlet bookServlet = new BookServlet();

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

        BookDao bookDao = mock(BookDao.class);
        bookServlet.bookDao = bookDao;
        bookServlet.doGet(request, response);
        verify(bookDao, atLeast(1)).get(1);

        verify(request, atLeast(1)).getParameter("id");
        assertNotNull(response);
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'save' ")
    public void doPostTest() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, DaoException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("2"); //put unique id here!
        when(request.getParameter("title")).thenReturn("testTitle");
        when(request.getParameter("publisher")).thenReturn("1");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        BookDao bookDao = mock(BookDao.class);
        bookServlet.bookDao = bookDao;
        bookServlet.doPost(request, response);
        verify(bookDao, atLeast(1)).save(isA(Book.class));

        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("title");
        assertNotNull(response);
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'update' ")
    public void doPut() throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException, DaoException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("title")).thenReturn("updtestTitle");
        when(request.getParameter("publisher")).thenReturn("1");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        BookDao bookDao = mock(BookDao.class);
        bookServlet.bookDao = bookDao;
        bookServlet.doPut(request, response);
        verify(bookDao, atLeast(1)).update(isA(Book.class));

        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("title");
        assertNotNull(response);
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'delete' ")
    public void doDelete() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, DaoException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("2");
        when(request.getParameter("title")).thenReturn("testTitle");
        when(request.getParameter("publisher")).thenReturn("1");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        BookDao bookDao = mock(BookDao.class);
        bookServlet.bookDao = bookDao;
        bookServlet.doDelete(request, response);
        verify(bookDao, atLeast(1)).delete(isA(Book.class));

        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("title");
        assertNotNull(response);
    }
}
