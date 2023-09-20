package service;

import dao.impl.BookDao;
import dao.impl.PublisherDao;
import dto.BookDto;
import dto.PublisherDto;
import exception.DaoException;
import exception.ServiceException;
import launch.AppConfig;
import mapper.BookDtoMapper;
import mapper.PublisherDtoMapper;
import mapper.PublisherDtoMapperImpl;
import model.Book;
import model.Publisher;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class PublisherServiceTest {
    PublisherDao publisherDao = mock(PublisherDao.class);
    PublisherDtoMapper publisherDtoMapper = mock(PublisherDtoMapperImpl.class);
    PublisherService publisherService = new PublisherService(publisherDao, publisherDtoMapper);


    @Test
    public void handleGetTest() throws DaoException, ServiceException {
        when(publisherDao.get(1L)).thenReturn(new Publisher());
        when(publisherDtoMapper.toDto(any())).thenReturn(new PublisherDto());

        publisherService.handleGetRequest(1);

        verify(publisherDao, atLeast(1)).get(1);
        verify(publisherDtoMapper, atLeast(1)).toDto(any());
    }

    @Test
    public void handlePostTest() throws DaoException, ServiceException, IOException {;
        publisherService.handlePostRequest(new Publisher());
        verify(publisherDao, atLeast(1)).save(any());
    }

    @Test
    public void handlePutTest() throws DaoException, ServiceException, IOException {
        publisherService.handlePutRequest(new Publisher());
        verify(publisherDao, atLeast(1)).update(any());
    }

    @Test
    public void handleDeleteTest() throws DaoException, ServiceException, IOException {
        publisherService.handleDeleteRequest(1L);
        verify(publisherDao, atLeast(1)).delete(any());
        verify(publisherDtoMapper, atLeast(1)).toPublisher(any());
    }
}
