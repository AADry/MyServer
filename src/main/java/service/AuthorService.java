package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.impl.AuthorDao;
import exception.DaoException;
import exception.ServiceException;
import model.Author;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorService {
    private static final Gson GSON = new GsonBuilder().create();
    protected AuthorDao authorDao = new AuthorDao();

    public String handleGetRequest(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long id = Long.valueOf(req.getParameter("id"));
        String json;
        try {
            Author author = authorDao.get(id);
            json = GSON.toJson(author);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
        return json;
    }

    public void handlePostRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = String.valueOf(req.getParameter("name"));
        Author author = new Author(id, name);
        try {
            authorDao.save(author);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void handlePutRequest(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = String.valueOf(req.getParameter("name"));
        Author author = new Author(id, name);
        try {
            authorDao.update(author);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void handleDeleteRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = String.valueOf(req.getParameter("name"));
        Author author = new Author(id, name);
        try {
            authorDao.delete(author);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
