package service;

import dao.impl.AuthorDao;
import dao.impl.BookDao;
import dto.AuthorDto;
import dto.BookDto;
import exception.DaoException;
import exception.ServiceException;
import launch.AppConfig;
import mapper.AuthorDtoMapper;
import mapper.BookDtoMapper;
import mapper.BookDtoMapperImpl;
import model.Author;
import model.Book;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class BookServiceTest {
    BookDao bookDao = mock(BookDao.class);
    BookDtoMapper bookDtoMapper = mock(BookDtoMapper.class);
    BookService bookService = new BookService(bookDao, bookDtoMapper);


    @Test
    public void handleGetTest() throws DaoException, ServiceException {
        when(bookDao.get(1L)).thenReturn(new Book());
        when(bookDtoMapper.toDTO(any())).thenReturn(new BookDto());

        bookService.handleGetRequest(1);

        verify(bookDao, atLeast(1)).get(1);
        verify(bookDtoMapper, atLeast(1)).toDTO(any());
    }

    @Test
    public void handlePostTest() throws DaoException, ServiceException, IOException {;
        bookService.handlePostRequest(new Book());
        verify(bookDao, atLeast(1)).save(any());
    }

    @Test
    public void handlePutTest() throws DaoException, ServiceException, IOException {
        bookService.handlePutRequest(new Book());
        verify(bookDao, atLeast(1)).update(any());
    }

    @Test
    public void handleDeleteTest() throws DaoException, ServiceException, IOException {
        bookService.handleDeleteRequest(1L);
        verify(bookDao, atLeast(1)).delete(any());
        verify(bookDtoMapper, atLeast(1)).toBook(any());
    }
}
