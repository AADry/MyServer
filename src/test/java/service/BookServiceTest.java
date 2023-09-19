package service;

import dao.impl.BookDao;
import exception.DaoException;
import exception.ServiceException;
import launch.AppConfig;
import model.Book;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class BookServiceTest {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    BookService bookService = applicationContext.getBean(BookService.class);
    BookDao bookDao = mock(BookDao.class);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);


    @Test
    public void handleGetTest() throws DaoException, ServiceException {
        when(bookDao.get(1)).thenReturn(new Book());
        bookService.bookDao = bookDao;
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        bookService.handleGetRequest(request, response);
        verify(bookDao, atLeast(1)).get(1);
        verify(request, atLeast(1)).getParameter("id");
    }

    @Test
    public void handlePostTest() throws DaoException, ServiceException, IOException {
        bookService.bookDao = bookDao;
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("title")).thenReturn("book1");
        when(request.getParameter("publisher")).thenReturn(String.valueOf(1));
        bookService.handlePostRequest(request, response);
        verify(bookDao, atLeast(1)).save(any());
        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("title");
        verify(request, atLeast(1)).getParameter("publisher");
    }

    @Test
    public void handlePutTest() throws DaoException, ServiceException, IOException {
        bookService.bookDao = bookDao;
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("title")).thenReturn("book1");
        when(request.getParameter("publisher")).thenReturn(String.valueOf(1));
        bookService.handlePutRequest(request, response);
        verify(bookDao, atLeast(1)).update(any());
        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("title");
        verify(request, atLeast(1)).getParameter("publisher");
    }

    @Test
    public void handleDeleteTest() throws DaoException, ServiceException, IOException {
        bookService.bookDao = bookDao;
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("title")).thenReturn("book1");
        when(request.getParameter("publisher")).thenReturn(String.valueOf(1));
        bookService.handleDeleteRequest(request, response);
        verify(bookDao, atLeast(1)).delete(any());
        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("title");
        verify(request, atLeast(1)).getParameter("publisher");

    }
}
