package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.impl.BookDao;
import exception.DaoException;
import exception.ServiceException;
import model.Book;
import model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Service
public class BookService {
    @Autowired
    private Gson GSON;
    @Autowired
    protected BookDao bookDao;

    public String handleGetRequest(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long id = Long.valueOf(req.getParameter("id"));
        String json;
        try {
            Book book = bookDao.get(id);
            json = GSON.toJson(book);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
        return json;
    }

    public void handlePostRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
        Long id = Long.valueOf(req.getParameter("id"));
        String title = String.valueOf(req.getParameter("title"));
        Long publisher_id = Long.valueOf(req.getParameter("publisher"));
        Book book = new Book();
        try {
            Publisher publisher = new Publisher();
            publisher.setId(publisher_id);
            book.setPublisher(publisher);
            book.setId(id);
            book.setTitle(title);
            bookDao.save(book);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void handlePutRequest(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long id = Long.valueOf(req.getParameter("id"));
        String title = String.valueOf(req.getParameter("title"));
        Long publisher_id = Long.valueOf(req.getParameter("publisher"));
        Book book = new Book();
        try {
            Publisher publisher = new Publisher();
            publisher.setId(publisher_id);
            book.setPublisher(publisher);
            book.setId(id);
            book.setTitle(title);
            bookDao.update(book);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void handleDeleteRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
        Long id = Long.valueOf(req.getParameter("id"));
        String title = String.valueOf(req.getParameter("title"));
        Long publisherId = Long.valueOf(req.getParameter("publisher"));
        Book book = new Book();
        try {
            Publisher publisher = new Publisher();
            publisher.setId(publisherId);
            book.setPublisher(publisher);
            book.setId(id);
            book.setTitle(title);
            bookDao.delete(book);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
