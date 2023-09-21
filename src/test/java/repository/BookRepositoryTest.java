package repository;

import launch.AppConfig;
import model.Book;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Testcontainers
public class BookRepositoryTest {
    @ClassRule
    @Container
    static public JdbcDatabaseContainer<?> container = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("small")
            .withUsername("postgres")
            .withPassword("secret")
            .withInitScript("init.sql");
    @Autowired
    BookRepository repository;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                () -> String.format("jdbc:postgresql://localhost:%d/small", container.getFirstMappedPort()));
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "secret");
    }

    @Test
    public void create() {
        Book book = new Book();
        book.setId(2L);
        book.setTitle("blah");
        repository.save(book);
        Assert.assertTrue(repository.findById(2L).isPresent());
    }

    @Test
    public void delete() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("blah");
        repository.save(book);
        repository.delete(book);
        Assert.assertTrue(repository.findById(1L).isEmpty());
    }

    @Test
    public void get() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("blah");
        repository.save(book);
        repository.findById(1L);
        Assert.assertTrue(repository.findById(1L).isPresent());
    }

    @Test
    public void getAll() {
        Assert.assertNotNull(repository.findAll().iterator());
    }
}
