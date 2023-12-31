package dao;

import dao.impl.PublisherDao;
import dbconnection.ConnectionManager;
import exception.DaoException;
import model.Publisher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Testcontainers
public class PublisherDaoTests {
    @Rule
    @Container
    public JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withInitScript("init.sql")
            .withDatabaseName("small")
            .withUsername("postgres")
            .withPassword("admin");
    PublisherDao publisherDao = new PublisherDao();

    @Before
    public void setUp() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("dbproperties.cfg"));
        properties.setProperty("url", postgreSQLContainer.getJdbcUrl());
        ConnectionManager.setProperties(properties);
    }

    @Test
    @DisplayName("should save Publisher in database then get it afterwards")
    public void save() throws DaoException {
        Publisher p = new Publisher();
        p.setId(2);
        p.setName("blah");
        p.setAddress("blah blah");
        publisherDao.save(p);
        Assert.assertNotNull(publisherDao.get(1));
    }

    @Test
    @DisplayName("should return list of publishers")
    public void getAll() throws DaoException {
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
