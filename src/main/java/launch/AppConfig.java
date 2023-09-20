package launch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.BookController;
import dao.impl.AuthorDao;
import dao.impl.BookDao;
import dao.impl.PublisherDao;
import dto.AuthorDto;
import mapper.AuthorDtoMapper;
import mapper.BookDtoMapper;
import mapper.BookDtoMapperImpl;
import mapper.PublisherDtoMapper;
import model.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.AuthorService;
import service.BookService;
import service.PublisherService;

@Configuration
public class AppConfig {
    @Bean
    public BookController bookController(){
        return new BookController(bookService());
    }
    @Bean
    public AuthorService authorService() {
        return new AuthorService(authorDao(),authorDtoMapper());
    }
    @Bean
    public BookService bookService(){
        return new BookService(bookDao(),bookDtoMapper());
    }
    @Bean
    public PublisherService publisherService(){
        return new PublisherService(publisherDao(),publisherDtoMapper());
    }
    @Bean
    public AuthorDao authorDao() {
        return new AuthorDao();
    }

    @Bean
    public BookDao bookDao() {
        return new BookDao();
    }

    @Bean
    public PublisherDao publisherDao() {
        return new PublisherDao();
    }
    @Bean
    public Gson gson(){
        return new GsonBuilder().create();
    }
    @Bean
    public BookDtoMapper bookDtoMapper(){
        return new BookDtoMapperImpl();
    }
    @Bean
    public AuthorDtoMapper authorDtoMapper(){
        return null;
    }
    @Bean
    public PublisherDtoMapper publisherDtoMapper(){
        return null;
    }
}
