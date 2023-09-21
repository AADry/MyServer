package repository;

import launch.AppConfig;
import model.Publisher;
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

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class PublisherRepositoryTest {
    @ClassRule
    @Container
    static public JdbcDatabaseContainer<?> container = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("small")
            .withUsername("postgres")
            .withPassword("secret")
            .withInitScript("init.sql");
    @Autowired
    PublisherRepository repository;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                () -> String.format("jdbc:postgresql://localhost:%d/small", container.getFirstMappedPort()));
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "secret");
    }

    @Test
    public void create() {
        Publisher publisher = new Publisher();
        publisher.setId(2);
        publisher.setName("test");
        publisher.setAddress("address");
        repository.save(publisher);
        Assert.assertTrue(repository.findById(2L).isPresent());
    }

    @Test
    public void delete() {
        Publisher publisher = new Publisher();
        publisher.setId(1);
        publisher.setName("test");
        publisher.setAddress("address");
        repository.delete(publisher);
        Assert.assertTrue(repository.findById(1L).isEmpty());
    }

    @Test
    public void get() {
        Publisher publisher = new Publisher();
        publisher.setId(3);
        publisher.setName("test");
        publisher.setAddress("address");
        repository.save(publisher);
        Assert.assertTrue(repository.findById(3L).isPresent());
    }

    @Test
    public void getAll() {
        Assert.assertNotNull(repository.findAll().iterator());
    }
}
