package controller;

import dto.AuthorDto;
import dto.BookDto;
import exception.ServiceException;
import launch.AppConfig;
import model.Author;
import model.Book;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import service.AuthorService;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class AuthorControllerTest {
    AuthorService service = mock(AuthorService.class);
    AuthorController controller = new AuthorController(service);

    @Test
    @DisplayName("should invoke service method handle get request")
    public void doGetTest() throws IOException, ServiceException {
        when(service.handleGetRequest(1L)).thenReturn(new AuthorDto());
        controller.getAuthor(1L);
        verify(service, atLeast(1)).handleGetRequest(1L);
    }
    @Test
    @DisplayName("should invoke service method handle post request")
    public void doPostTest() throws IOException, ServiceException {

        controller.postAuthor(new Author());
        verify(service, atLeast(1)).handlePostRequest(any());
    }

    @Test
    @DisplayName("should invoke service method handle put request")
    public void doPut() throws IOException, ServiceException {
        controller.updateAuthor(new Author());
        verify(service, atLeast(1)).handlePutRequest(any());

    }

    @Test
    @DisplayName("should invoke service method handle delete request")
    public void doDelete() throws IOException, ServiceException {
        when(service.handleGetRequest(1L)).thenReturn(new AuthorDto());
        controller.deleteAuthor(1L);
        verify(service, atLeast(1)).handleDeleteRequest(1L);

    }
}
