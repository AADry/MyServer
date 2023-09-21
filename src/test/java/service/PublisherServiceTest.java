package service;

import dto.PublisherDto;
import exception.DaoException;
import exception.ServiceException;
import mapper.PublisherDtoMapper;
import mapper.PublisherDtoMapperImpl;
import model.Publisher;
import org.junit.Test;
import repository.PublisherRepository;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class PublisherServiceTest {
    PublisherRepository repository = mock(PublisherRepository.class);
    PublisherDtoMapper publisherDtoMapper = mock(PublisherDtoMapperImpl.class);
    PublisherService publisherService = new PublisherService(publisherDtoMapper);


    @Test
    public void handleGetTest() throws DaoException, ServiceException {
        publisherService.publisherRepository = repository;
        when(repository.findById(1L)).thenReturn(Optional.of(new Publisher()));
        when(publisherDtoMapper.toDto(any())).thenReturn(new PublisherDto());

        publisherService.handleGetRequest(1);

        verify(repository, atLeast(1)).findById(1L);
        verify(publisherDtoMapper, atLeast(1)).toDto(any());
    }

    @Test
    public void handlePostTest() throws DaoException, ServiceException, IOException {
        publisherService.publisherRepository = repository;
        publisherService.handlePostRequest(new Publisher());
        verify(repository, atLeast(1)).save(any());
    }

    @Test
    public void handlePutTest() throws DaoException, ServiceException, IOException {
        publisherService.publisherRepository = repository;
        publisherService.handlePutRequest(new Publisher());
        verify(repository, atLeast(1)).save(any());
    }

    @Test
    public void handleDeleteTest() throws DaoException, ServiceException, IOException {
        publisherService.publisherRepository = repository;
        publisherService.handleDeleteRequest(1L);
        verify(repository, atLeast(1)).delete(any());
        verify(publisherDtoMapper, atLeast(1)).toPublisher(any());
    }
}
