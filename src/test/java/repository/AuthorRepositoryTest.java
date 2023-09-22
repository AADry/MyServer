package repository;

import launch.AppConfig;
import model.Author;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@Testcontainers
@ContextConfiguration(classes = AppConfig.class)
public class AuthorRepositoryTest {
    @ClassRule
    @Container
    static public JdbcDatabaseContainer<?> container = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("small")
            .withUsername("postgres")
            .withPassword("secret")
            .withInitScript("init.sql");
    @Autowired
    AuthorRepository repository;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                () -> String.format("jdbc:postgresql://localhost:%d/small", container.getFirstMappedPort()));
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "secret");
    }

    @Test
    public void create() {
        Author author = new Author();
        author.setId(2L);
        author.setName("blah");
        List<Book> books = new ArrayList<Book>();
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        repository.save(author);
        Assert.assertTrue(repository.findById(2L).isPresent());

    }

    @Test
    public void delete() {
        Author author = new Author();
        author.setId(2L);
        author.setName("blah");
        repository.save(author);
        repository.delete(author);
        Assert.assertTrue(repository.findById(1L).isEmpty());
    }

    @Test
    public void get() {
        Author author = new Author();
        author.setId(2L);
        author.setName("blah");
        repository.save(author);
        author = repository.findById(2L).get();
        Assert.assertTrue(repository.findById(2L).isPresent());
    }

    @Test
    public void getAll() {
        Assert.assertNotNull(repository.findAll().iterator());
    }
}
