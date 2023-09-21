package service;

import dto.PublisherDto;
import exception.ServiceException;
import mapper.PublisherDtoMapper;
import model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PublisherRepository;

import java.io.IOException;
import java.util.Optional;

@Service
public class PublisherService {
    @Autowired
    protected PublisherRepository publisherRepository;
    private final PublisherDtoMapper mapper;

    @Autowired
    public PublisherService(PublisherDtoMapper mapper) {
        this.mapper = mapper;
    }


    public PublisherDto handleGetRequest(long id) throws ServiceException {
        PublisherDto publisherDto;
        Optional<Publisher> publisher = publisherRepository.findById(id);
        if (publisher.isPresent()) {
            publisherDto = mapper.toDto(publisher.get());
            return publisherDto;
        } else {
            throw new ServiceException("not found");
        }

    }

    public void handlePostRequest(Publisher publisher) throws IOException, ServiceException {
        publisherRepository.save(publisher);
    }

    public void handlePutRequest(Publisher publisher) throws ServiceException {
        publisherRepository.save(publisher);
    }

    public void handleDeleteRequest(long id) throws IOException, ServiceException {
        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setId(id);
        publisherRepository.delete(mapper.toPublisher(publisherDto));
    }
}
