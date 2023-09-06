package service;

import dao.impl.AuthorDao;
import exception.DaoException;
import exception.ServiceException;
import model.Author;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class AuthorServiceTest {
    AuthorService authorService = new AuthorService();
    AuthorDao authorDao = mock(AuthorDao.class);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);


    @Test
    public void handleGetTest() throws DaoException, ServiceException {
        when(authorDao.get(1L)).thenReturn(new Author());
        authorService.authorDao = authorDao;
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        authorService.handleGetRequest(request, response);
        verify(authorDao, atLeast(1)).get(1);
        verify(request, atLeast(1)).getParameter("id");
    }
    @Test
    public void should_throw_serviceException_handling_get_request() throws DaoException, ServiceException {
        when(authorDao.get(1L)).thenThrow(DaoException.class);
        authorService.authorDao = authorDao;
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        Assert.assertThrows(ServiceException.class, () -> authorService.handleGetRequest(request,response));
    }

    @Test
    public void handlePostTest() throws DaoException, ServiceException, IOException {
        authorService.authorDao = authorDao;
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("name")).thenReturn("Vasily");
        authorService.handlePostRequest(request, response);
        verify(authorDao, atLeast(1)).save(any());
        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("name");
    }

    @Test
    public void handlePutTest() throws DaoException, ServiceException {
        authorService.authorDao = authorDao;
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("name")).thenReturn("Vasily");
        authorService.handlePutRequest(request, response);
        verify(authorDao, atLeast(1)).update(any());
        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("name");
    }

    @Test
    public void handleDeleteTest() throws DaoException, ServiceException, IOException {
        authorService.authorDao = authorDao;
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("name")).thenReturn("Vasily");
        authorService.handleDeleteRequest(request, response);
        verify(authorDao, atLeast(1)).delete(any());
        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("name");
    }
}
