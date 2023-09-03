package dao;

import dao.impl.AuthorDao;
import dao.impl.BookAuthorDao;
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
import org.junit.jupiter.api.DisplayName;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BookDaoTests {
    @Rule
    public JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withInitScript("init.sql")
            .withDatabaseName("small")
            .withUsername("postgres")
            .withPassword("admin");

    @Before
    public void setUp() {
        Properties properties = new Properties();
        properties.setProperty("url", postgreSQLContainer.getJdbcUrl());
        properties.setProperty("username", postgreSQLContainer.getUsername());
        properties.setProperty("password", postgreSQLContainer.getPassword());
        ConnectionManager.setProperties(properties);
    }

    @Test
    @DisplayName("should save Book in database then get it afterwards")
    public void save() throws DaoException {
        BookDao bookDao = new BookDao();
        PublisherDao publisherDao = new PublisherDao();
        Publisher p = new Publisher();
        p.setId(2);
        p.setName("blah");
        p.setAddress("blah blah");
        publisherDao.save(p);
        Book b = new Book();
        b.setId(2);
        b.setTitle("blah");
        b.setPublisher(p);
        List<Author> authors = new ArrayList<>();
        Author author = new Author();
        author.setId(2);
        author.setName("testAuthorName");
        authors.add(author);
        AuthorDao authorDao = new AuthorDao();
        authorDao.save(author);
        b.setAuthors(authors);
        bookDao.save(b);
        Assert.assertNotNull(publisherDao.get(2));
        Assert.assertNotNull(bookDao.get(2));
    }

    @Test
    @DisplayName("should return list of books")
    public void getAll() throws DaoException {
        BookDao bookDao = new BookDao();
        PublisherDao publisherDao = new PublisherDao();
        AuthorDao authorDao = new AuthorDao();
        for (int i = 2; i < 111; i++) {
            Publisher p = new Publisher();
            p.setId(i);
            p.setName("blah");
            p.setAddress("blah blah");
            publisherDao.save(p);
            Book b = new Book();
            b.setId(i);
            b.setTitle("blah");
            b.setPublisher(p);
            List<Author> authors = new ArrayList<>();
            Author author = new Author();
            author.setId(i);
            author.setName("testAuthorName");
            authorDao.save(author);
            authors.add(author);
            b.setAuthors(authors);
            bookDao.save(b);
        }
        List<Book> books = bookDao.getAll();
        Assert.assertNotNull(books);
        Assert.assertNotNull(books.get(55));
    }

    @Test
    @DisplayName("blah blah")
    public void delete() throws DaoException {
        BookDao bookDao = new BookDao();
        PublisherDao publisherDao = new PublisherDao();
        AuthorDao authorDao = new AuthorDao();
        Publisher p = new Publisher();
        p.setId(2);
        p.setName("blah");
        p.setAddress("blah blah");
        publisherDao.save(p);
        Book b = new Book();
        b.setId(2);
        b.setTitle("blah");
        List<Author> authors = new ArrayList<>();
        Author author = new Author();
        author.setId(2);
        author.setName("testAuthorName");
        authors.add(author);
        authorDao.save(author);
        b.setPublisher(p);
        b.setAuthors(authors);
        bookDao.save(b);
        bookDao.delete(b);
        Assert.assertThrows(DaoException.class, () -> {
            bookDao.get(2);
        });
    }

    @Test
    @DisplayName("blah")
    public void update() throws DaoException {
        BookDao bookDao = new BookDao();
        PublisherDao publisherDao = new PublisherDao();
        Publisher p = new Publisher();
        p.setId(2);
        p.setName("blah");
        p.setAddress("blah blah");
        publisherDao.save(p);
        Book b = new Book();
        b.setId(2);
        b.setTitle("blah");
        b.setPublisher(p);
        List<Author> authors = new ArrayList<>();
        Author author = new Author();
        author.setId(2);
        author.setName("testAuthorName");
        authors.add(author);
        bookDao.save(b);
        Assert.assertEquals("blah", bookDao.get(2).getTitle());
        b.setTitle("terry");
        b.setAuthors(authors);
        bookDao.update(b);
        Assert.assertEquals("terry", bookDao.get(2).getTitle());
    }
}
