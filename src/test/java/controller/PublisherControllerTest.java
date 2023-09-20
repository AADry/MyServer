package controller;

import dto.BookDto;
import dto.PublisherDto;
import exception.ServiceException;
import launch.AppConfig;
import model.Book;
import model.Publisher;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import service.BookService;
import service.PublisherService;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class PublisherControllerTest {
    private final PublisherService service = mock(PublisherService.class);
    private final PublisherController controller = new PublisherController(service);

    @Test
    @DisplayName("should invoke service method handle get request")
    public void doGetTest() throws IOException, ServiceException {
        when(service.handleGetRequest(1L)).thenReturn(new PublisherDto());
        controller.getPublisher(1L);
        verify(service, atLeast(1)).handleGetRequest(1L);
    }
    @Test
    @DisplayName("should invoke service method handle post request")
    public void doPostTest() throws IOException, ServiceException {

        controller.postPublisher(new Publisher());
        verify(service, atLeast(1)).handlePostRequest(any());
    }

    @Test
    @DisplayName("should invoke service method handle put request")
    public void doPut() throws IOException, ServiceException {
        controller.updatePublisher(new Publisher());
        verify(service, atLeast(1)).handlePutRequest(any());

    }

    @Test
    @DisplayName("should invoke service method handle delete request")
    public void doDelete() throws IOException, ServiceException {
        when(service.handleGetRequest(1L)).thenReturn(new PublisherDto());
        controller.deletePublisher(1L);
        verify(service, atLeast(1)).handleDeleteRequest(1L);

    }
}
