package launch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.impl.AuthorDao;
import dao.impl.BookDao;
import dao.impl.PublisherDao;
import mapper.BookDtoMapper;
import mapper.BookDtoMapperImpl;
import model.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.AuthorService;
import service.BookService;
import service.PublisherService;

@Configuration
public class AppConfig {
    @Bean
    public AuthorService authorService() {
        return new AuthorService();
    }
    @Bean
    public BookService bookService(){
        return new BookService();
    }
    @Bean
    public PublisherService publisherService(){
        return new PublisherService();
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
}
