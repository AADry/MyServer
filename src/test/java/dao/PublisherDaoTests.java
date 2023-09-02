package dao;

import dao.impl.PublisherDao;
import dbconnection.ConnectionManager;
import exception.DaoException;
import model.Publisher;
import org.junit.*;
import org.junit.jupiter.api.DisplayName;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

public class PublisherDaoTests {
    @Rule
    public JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withInitScript("init.sql")
            .withDatabaseName("small")
            .withUsername("postgres")
            .withPassword("admin");

    @Test
    @DisplayName("should save Publisher in database then get it afterwards")
    public void save() throws DaoException {
        ConnectionManager.setProperties(postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword());
        PublisherDao publisherDao = new PublisherDao();
        Publisher p = new Publisher();
        p.setId(2);
        p.setName("blah");
        p.setAddress("blah blah");
        publisherDao.save(p);
        Assert.assertNotNull(publisherDao.get(1));
        //publisherDao.save((Publisher) new Object());
    }

    @Test
    @DisplayName("should return list of publishers")
    public void getAll() throws DaoException {
        ConnectionManager.setProperties(postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword());
        PublisherDao publisherDao = new PublisherDao();
        for (int i = 2; i < 111; i++) {
            Publisher p = new Publisher();
            p.setId(i);
            p.setName("blah");
            p.setAddress("blah blah");
            publisherDao.save(p);
        }
        List<Publisher> publisherList = publisherDao.getAll();
        Assert.assertNotNull(publisherList);
        Assert.assertNotNull(publisherList.get(55));
    }

    @Test
    @DisplayName("should delete publisher from db")
    public void delete() throws DaoException {
        ConnectionManager.setProperties(postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword());
        PublisherDao publisherDao = new PublisherDao();
        Publisher p = new Publisher();
        p.setId(2);
        p.setName("blah");
        p.setAddress("blah blah");
        publisherDao.save(p);
        publisherDao.delete(p);
        Assert.assertThrows(DaoException.class, () -> {
            publisherDao.get(2);
        });
    }

    @Test
    @DisplayName("should update publisher in db")
    public void update() throws DaoException {
        ConnectionManager.setProperties(postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword());
        PublisherDao publisherDao = new PublisherDao();
        Publisher p = new Publisher();
        p.setId(2);
        p.setName("switch");
        p.setAddress("blah blah");
        publisherDao.save(p);
        p.setName("case");
        publisherDao.update(p);
        Assert.assertEquals("case", publisherDao.get(2).getName());
    }
    //}
}
