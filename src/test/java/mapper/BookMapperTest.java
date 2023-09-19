package mapper;

import dto.BookDto;
import launch.AppConfig;
import model.Author;
import model.Book;
import model.Publisher;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

public class BookMapperTest {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    BookDtoMapper mapper = applicationContext.getBean(BookDtoMapperImpl.class);
    @Test
    public void givenBookEntity_whenMapsToDto_thenCorrect(){
        Book book = new Book();
        book.setTitle("testTitle");
        book.setPublisher(mock(Publisher.class));
        book.setAuthors(new ArrayList<Author>());
        book.setId(1);
        BookDto bookDto = mapper.toDTO(book);
        Assert.assertEquals("testTitle",bookDto.getTitle());
        Assert.assertEquals(1,bookDto.getId());
    }
    @Test
    public void givenBookDTO_whenMapsToEntity_thenCorrect(){
        BookDto bookDto = new BookDto();
        bookDto.setTitle("testTitle");
        bookDto.setPublisher(mock(Publisher.class));
        bookDto.setAuthors(new ArrayList<Author>());
        bookDto.setId(1);
        Book book = mapper.toBook(bookDto);
        Assert.assertEquals("testTitle",book.getTitle());
        Assert.assertEquals(1,book.getId());
    }
}
