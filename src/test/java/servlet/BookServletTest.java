package servlet;

import exception.ServiceException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import service.BookService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class BookServletTest extends Mockito {
    BookServlet bookServlet = new BookServlet();


    @Test
    @DisplayName("Should take params from request and invoke service method 'get' ")
    public void doGetTest() throws IOException, ServiceException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        BookService bookService = mock(BookService.class);
        bookServlet.bookService = bookService;
        bookServlet.doGet(request, response);
        verify(bookService, atLeast(1)).handleGetRequest(request, response);

    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'save' ")
    public void doPostTest() throws IOException, ServiceException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("2"); //put unique id here!
        when(request.getParameter("title")).thenReturn("testTitle");
        when(request.getParameter("publisher")).thenReturn("1");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        BookService bookService = mock(BookService.class);
        bookServlet.bookService = bookService;
        bookServlet.doPost(request, response);
        verify(bookService, atLeast(1)).handlePostRequest(request, response);
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'update' ")
    public void doPut() throws IOException, ServiceException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("title")).thenReturn("updtestTitle");
        when(request.getParameter("publisher")).thenReturn("1");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        BookService bookService = mock(BookService.class);
        bookServlet.bookService = bookService;
        bookServlet.doPut(request, response);
        verify(bookService, atLeast(1)).handlePutRequest(request, response);

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

        BookService bookService = mock(BookService.class);
        bookServlet.bookService = bookService;
        bookServlet.doDelete(request, response);
        verify(bookService, atLeast(1)).handleDeleteRequest(request, response);

    }
}
