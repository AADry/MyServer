package service;

import dto.AuthorDto;
import exception.DaoException;
import exception.ServiceException;
import mapper.AuthorDtoMapper;
import model.Author;
import org.junit.Test;
import repository.AuthorRepository;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class AuthorServiceTest {

    AuthorRepository repository = mock(AuthorRepository.class);
    AuthorDtoMapper authorDtoMapper = mock(AuthorDtoMapper.class);
    AuthorService authorService = new AuthorService(authorDtoMapper);


    @Test
    public void handleGetTest() throws DaoException, ServiceException {
        authorService.authorRepository = repository;
        when(repository.findById(1L)).thenReturn(Optional.of(new Author()));
        when(authorDtoMapper.toDto(any())).thenReturn(new AuthorDto());

        authorService.handleGetRequest(1);

        verify(repository, atLeast(1)).findById(1L);
        verify(authorDtoMapper, atLeast(1)).toDto(any());
    }

    @Test
    public void handlePostTest() throws DaoException, ServiceException, IOException {
        authorService.authorRepository = repository;
        authorService.handlePostRequest(new Author());
        verify(repository, atLeast(1)).save(any());
    }

    @Test
    public void handlePutTest() throws DaoException, ServiceException, IOException {
        authorService.authorRepository = repository;
        authorService.handlePostRequest(new Author());
        verify(repository, atLeast(1)).save(any());
    }

    @Test
    public void handleDeleteTest() throws DaoException, ServiceException, IOException {
        authorService.authorRepository = repository;
        authorService.handleDeleteRequest(1L);
        verify(repository, atLeast(1)).delete(any());
        verify(authorDtoMapper, atLeast(1)).toAuthor(any());
    }
}
