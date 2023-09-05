package servlet;

import exception.ServiceException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import service.PublisherService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class PublisherServletTest {
    PublisherServlet publisherServlet = new PublisherServlet();

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
        verify(publisherService, atLeast(1)).handleGetRequest(request, response);
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'save' ")
    public void doPostTest() throws IOException, ServiceException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("2"); //put unique id here!
        when(request.getParameter("name")).thenReturn("testName");
        when(request.getParameter("address")).thenReturn("testAddress");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        PublisherService publisherService = mock(PublisherService.class);
        publisherServlet.publisherService = publisherService;
        publisherServlet.doPost(request, response);
        verify(publisherService, atLeast(1)).handlePostRequest(request, response);
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'update' ")
    public void doPut() throws IOException, ServiceException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("testName");
        when(request.getParameter("address")).thenReturn("testAddress");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        PublisherService publisherService = mock(PublisherService.class);
        publisherServlet.publisherService = publisherService;
        publisherServlet.doPut(request, response);
        verify(publisherService, atLeast(1)).handlePutRequest(request, response);
    }

    @Test
    @DisplayName("Should take params from request and invoke service method 'delete' ")
    public void doDelete() throws IOException, ServiceException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("2");
        when(request.getParameter("name")).thenReturn("testName");
        when(request.getParameter("address")).thenReturn("testAddress");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        PublisherService publisherService = mock(PublisherService.class);
        publisherServlet.publisherService = publisherService;
        publisherServlet.doDelete(request, response);
        verify(publisherService, atLeast(1)).handleDeleteRequest(request, response);
    }
}
