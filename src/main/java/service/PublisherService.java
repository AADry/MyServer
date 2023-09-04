package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.impl.PublisherDao;
import exception.DaoException;
import exception.ServiceException;
import model.Author;
import model.Publisher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PublisherService {
    private static final Gson GSON = new GsonBuilder().create();
    PublisherDao publisherDao = new PublisherDao();

    public String handleGetRequest(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long id = Long.valueOf(req.getParameter("id"));
        String json;
        try {
            Publisher publisher = publisherDao.get(id);
            json = GSON.toJson(publisher);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
        return json;
    }

    public void handlePostRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = String.valueOf(req.getParameter("name"));
        String address = String.valueOf(req.getParameter("address"));
        Publisher publisher = new Publisher(id, name, address);
        try {
            publisherDao.save(publisher);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void handlePutRequest(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = String.valueOf(req.getParameter("name"));
        String address = String.valueOf(req.getParameter("address"));
        Publisher publisher = new Publisher(id, name, address);
        try {
            publisherDao.update(publisher);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void handleDeleteRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = String.valueOf(req.getParameter("name"));
        String address = String.valueOf(req.getParameter("address"));
        Publisher publisher = new Publisher(id, name, address);
        try {
            publisherDao.delete(publisher);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
