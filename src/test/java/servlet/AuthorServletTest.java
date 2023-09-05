package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exception.DaoException;
import exception.ServiceException;
import model.Author;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import service.AuthorService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;

import static org.mockito.Mockito.*;

public class AuthorServletTest {

    AuthorServlet authorServlet = new AuthorServlet();
    private static final Gson GSON = new GsonBuilder().create();


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
    public void doDelete() throws IOException, ServiceException {

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
