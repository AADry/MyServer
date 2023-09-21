package launch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AuthorController;
import controller.BookController;
import controller.PublisherController;
import mapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import service.AuthorService;
import service.BookService;
import service.PublisherService;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "repository")
@EnableTransactionManagement
public class AppConfig {
    @Bean
    public AuthorController authorController() {
        return new AuthorController(authorService());
    }

    @Bean
    PublisherController publisherController() {
        return new PublisherController(publisherService());
    }

    @Bean
    public BookController bookController() {
        return new BookController(bookService());
    }

    @Bean
    public AuthorService authorService() {
        return new AuthorService(authorDtoMapper());
    }

    @Bean
    public BookService bookService() {
        return new BookService(bookDtoMapper());
    }

    @Bean
    public PublisherService publisherService() {
        return new PublisherService(publisherDtoMapper());
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }

    @Bean
    public BookDtoMapper bookDtoMapper() {
        return new BookDtoMapperImpl();
    }

    @Bean
    public AuthorDtoMapper authorDtoMapper() {
        return new AuthorDtoMapperImpl();
    }

    @Bean
    public PublisherDtoMapper publisherDtoMapper() {
        return new PublisherDtoMapperImpl();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("model");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        return properties;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/smalldb");
        dataSource.setUsername("postgres");
        dataSource.setPassword("admin");
        return dataSource;
    }

}
