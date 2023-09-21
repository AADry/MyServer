package controller;

import dto.PublisherDto;
import exception.ServiceException;
import model.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import service.PublisherService;

import java.io.IOException;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    protected final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    public PublisherDto getPublisher(long id) {
        try {
            return publisherService.handleGetRequest(id);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Publisher not found",
                    e);
        }
    }

    public void deletePublisher(long id) {
        try {
            publisherService.handleDeleteRequest(id);
        } catch (ServiceException | IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Publisher not found",
                    e);
        }
    }

    public void updatePublisher(@RequestBody Publisher publisher) {
        try {
            publisherService.handlePutRequest(publisher);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Publisher not found",
                    e);
        }
    }

    public void postPublisher(@RequestBody Publisher publisher) {
        try {
            publisherService.handlePostRequest(publisher);
        } catch (ServiceException | IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Unable to create",
                    e);
        }
    }
}
