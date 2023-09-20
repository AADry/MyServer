package controller;

import dto.BookDto;
import exception.ServiceException;
import launch.AppConfig;
import model.Book;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import service.BookService;


import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class BookControllerTest {
    private final BookService service = mock(BookService.class);
    private final BookController controller = new BookController(service);

    @Test
    @DisplayName("should invoke service method handle get request")
    public void doGetTest() throws IOException, ServiceException {
        when(service.handleGetRequest(1L)).thenReturn(new BookDto());
        controller.getBook(1L);
        verify(service, atLeast(1)).handleGetRequest(1L);
    }

    @Test
    @DisplayName("should invoke service method handle post request")
    public void doPostTest() throws IOException, ServiceException {

        controller.postBook(new Book());
        verify(service, atLeast(1)).handlePostRequest(any());
    }

    @Test
    @DisplayName("should invoke service method handle put request")
    public void doPut() throws IOException, ServiceException {
        controller.updateBook(new Book());
        verify(service, atLeast(1)).handlePutRequest(any());
    }

    @Test
    @DisplayName("should invoke service method handle delete request")
    public void doDelete() throws IOException, ServiceException {
        when(service.handleGetRequest(1L)).thenReturn(new BookDto());
        controller.deleteBook(1L);
        verify(service, atLeast(1)).handleDeleteRequest(1L);
    }
}
