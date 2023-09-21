package service;

import dto.BookDto;
import exception.DaoException;
import exception.ServiceException;
import mapper.BookDtoMapper;
import model.Book;
import org.junit.Test;
import repository.BookRepository;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class BookServiceTest {
    BookRepository repository = mock(BookRepository.class);
    BookDtoMapper bookDtoMapper = mock(BookDtoMapper.class);
    BookService bookService = new BookService(bookDtoMapper);


    @Test
    public void handleGetTest() throws DaoException, ServiceException {
        bookService.bookRepository = repository;
        when(repository.findById(1L)).thenReturn(Optional.of(new Book()));
        when(bookDtoMapper.toDTO(any())).thenReturn(new BookDto());

        bookService.handleGetRequest(1);

        verify(repository, atLeast(1)).findById(1L);
        verify(bookDtoMapper, atLeast(1)).toDTO(any());
    }

    @Test
    public void handlePostTest() throws DaoException, ServiceException, IOException {;
        bookService.bookRepository = repository;
        bookService.handlePostRequest(new Book());
        verify(repository, atLeast(1)).save(any());
    }

    @Test
    public void handlePutTest() throws DaoException, ServiceException, IOException {
        bookService.bookRepository = repository;
        bookService.handlePutRequest(new Book());
        verify(repository, atLeast(1)).save(any());
    }

    @Test
    public void handleDeleteTest() throws DaoException, ServiceException, IOException {
        bookService.bookRepository = repository;
        bookService.handleDeleteRequest(1L);
        verify(repository, atLeast(1)).delete(any());
        verify(bookDtoMapper, atLeast(1)).toBook(any());
    }
}
