package dao;

import dao.impl.AuthorDao;
import dao.impl.BookDao;
import dao.impl.PublisherDao;
import dbconnection.ConnectionManager;
import exception.DaoException;
import model.Author;
import model.Book;
import model.Publisher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Testcontainers
public class AuthorDaoTests {
    @Rule
    @Container
    public JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withInitScript("init.sql")
            .withDatabaseName("small")
            .withUsername("postgres")
            .withPassword("admin");

    @Before
    public void setUp() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("dbproperties.cfg"));
        properties.setProperty("url", postgreSQLContainer.getJdbcUrl());
        ConnectionManager.setProperties(properties);
    }

    @Test
    public void save() throws DaoException {
        AuthorDao authorDao = new AuthorDao();
        List<Book> list = new ArrayList<>();
        Author author = new Author();
        author.setId(2);
        author.setName("blah");
        authorDao.save(author);
        Assert.assertNotNull(authorDao.get(1));
    }

    @Test
    public void delete() throws DaoException {
        AuthorDao authorDao = new AuthorDao();
        Author author = new Author();
        author.setId(1);
        authorDao.delete(author);
        Assert.assertThrows(DaoException.class, () -> {
            authorDao.get(1);
        });
    }

    @Test
    public void update() throws DaoException {
        AuthorDao authorDao = new AuthorDao();
        BookDao bookDao = new BookDao();
        PublisherDao publisherDao = new PublisherDao();
        Publisher p = new Publisher(2, "1", "1");
        publisherDao.save(p);
        Book b = new Book();
        b.setPublisher(p);
        List<Book> books = new ArrayList<>();
        b.setId(2);
        b.setTitle("blah");
        books.add(b);
        Author author = new Author();
        author.setBooks(books);
        bookDao.save(b);
        author.setId(1);
        Assert.assertEquals("author1", authorDao.get(1).getName());
        author.setName("terry");
        authorDao.update(author);
        Assert.assertEquals("terry", authorDao.get(1).getName());
    }

    @Test
    public void getAll() throws DaoException {
        BookDao bookDao = new BookDao();
        AuthorDao authorDao = new AuthorDao();
        PublisherDao publisherDao = new PublisherDao();
        for (int i = 2; i < 111; i++) {
            Publisher p = new Publisher(i, "1", "1");
            publisherDao.save(p);
            Book b = new Book();
            b.setPublisher(p);
            b.setId(i);
            b.setTitle("blah");
            List<Book> books = new ArrayList<>();
            books.add(b);
            bookDao.save(b);
            Author author = new Author();
            author.setId(i);
            author.setName("testAuthorName");
            author.setBooks(books);
            authorDao.save(author);
        }
        List<Author> authors = authorDao.getAll();
        Assert.assertNotNull(authors);
        Assert.assertNotNull(authors.get(55));
    }
}
