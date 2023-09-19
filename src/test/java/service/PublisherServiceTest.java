package service;

import dao.impl.PublisherDao;
import exception.DaoException;
import exception.ServiceException;
import launch.AppConfig;
import model.Publisher;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class PublisherServiceTest {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    PublisherService publisherService = applicationContext.getBean(PublisherService.class);
    PublisherDao publisherDao = mock(PublisherDao.class);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    @Test
    public void handleGetTest() throws DaoException, ServiceException {
        when(publisherDao.get(1L)).thenReturn(new Publisher());
        publisherService.publisherDao = publisherDao;
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        publisherService.handleGetRequest(request, response);
        verify(publisherDao, atLeast(1)).get(1);
        verify(request, atLeast(1)).getParameter("id");
    }

    @Test
    public void handlePostTest() throws DaoException, ServiceException, IOException {
        publisherService.publisherDao = publisherDao;
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("name")).thenReturn("testName");
        when(request.getParameter("address")).thenReturn("address");
        publisherService.handlePostRequest(request, response);
        verify(publisherDao, atLeast(1)).save(any());
        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("name");
        verify(request, atLeast(1)).getParameter("address");
    }

    @Test
    public void handlePutTest() throws DaoException, ServiceException{
        publisherService.publisherDao = publisherDao;
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("name")).thenReturn("testName");
        when(request.getParameter("address")).thenReturn("address");
        publisherService.handlePutRequest(request, response);
        verify(publisherDao, atLeast(1)).update(any());
        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("name");
        verify(request, atLeast(1)).getParameter("address");
    }

    @Test
    public void handleDeleteTest() throws DaoException, ServiceException, IOException {
        publisherService.publisherDao = publisherDao;
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("name")).thenReturn("testName");
        when(request.getParameter("address")).thenReturn("address");
        publisherService.handleDeleteRequest(request, response);
        verify(publisherDao, atLeast(1)).delete(any());
        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("name");
        verify(request, atLeast(1)).getParameter("address");
    }

}
