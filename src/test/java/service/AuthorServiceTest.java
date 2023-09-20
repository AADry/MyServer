package service;

import dao.impl.AuthorDao;
import dto.AuthorDto;
import exception.DaoException;
import exception.ServiceException;
import launch.AppConfig;
import mapper.AuthorDtoMapper;
import model.Author;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class AuthorServiceTest {

    AuthorDao authorDao = mock(AuthorDao.class);
    AuthorDtoMapper authorDtoMapper = mock(AuthorDtoMapper.class);
    AuthorService authorService = new AuthorService(authorDao, authorDtoMapper);


    @Test
    public void handleGetTest() throws DaoException, ServiceException {
        when(authorDao.get(1L)).thenReturn(new Author());
        when(authorDtoMapper.toDto(any())).thenReturn(new AuthorDto());

        authorService.handleGetRequest(1);

        verify(authorDao, atLeast(1)).get(1);
        verify(authorDtoMapper, atLeast(1)).toDto(any());
    }

    @Test
    public void handlePostTest() throws DaoException, ServiceException, IOException {;
        authorService.handlePostRequest(new Author());
        verify(authorDao, atLeast(1)).save(any());
    }

    @Test
    public void handlePutTest() throws DaoException, ServiceException, IOException {
        authorService.handlePostRequest(new Author());
        verify(authorDao, atLeast(1)).update(any());
    }

    @Test
    public void handleDeleteTest() throws DaoException, ServiceException, IOException {
        authorService.handleDeleteRequest(1L);
        verify(authorDao, atLeast(1)).delete(any());
        verify(authorDtoMapper, atLeast(1)).toAuthor(any());
    }
}
