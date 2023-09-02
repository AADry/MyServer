package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.impl.BookDao;
import dao.impl.PublisherDao;
import exception.DaoException;
import model.Book;
import model.Publisher;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "BookServlet",
        urlPatterns = {"/books/*"}
)
public class BookServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.valueOf(req.getParameter("id"));
        BookDao bookDao = new BookDao();
        try {
            Book book = bookDao.get(id);
            String json = GSON.toJson(book);
            resp.setStatus(200);
            resp.setHeader("Content-Type", "application/json");
            resp.getOutputStream().println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.valueOf(req.getParameter("id"));
        String title = String.valueOf(req.getParameter("title"));
        Long publisher_id = Long.valueOf(req.getParameter("publisher"));
        BookDao bookDao = new BookDao();
        Book book = new Book();
        try {
            Publisher publisher = new Publisher();
            publisher.setId(publisher_id);
            book.setPublisher(publisher);
            book.setId(id);
            book.setTitle(title);
            bookDao.save(book);
            resp.setStatus(200);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.valueOf(req.getParameter("id"));
        String title = String.valueOf(req.getParameter("title"));
        Book book = new Book();
        BookDao bookDao = new BookDao();
        book.setId(id);
        book.setTitle(title);
        try {
            bookDao.delete(book);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.valueOf(req.getParameter("id"));
        String title = String.valueOf(req.getParameter("title"));
        Long publisher_id = Long.valueOf(req.getParameter("publisher"));
        Publisher publisher = new Publisher();
        publisher.setId(publisher_id);
        Book book = new Book();
        book.setPublisher(publisher);
        BookDao bookDao = new BookDao();
        book.setTitle(title);
        book.setId(id);
        try {
            bookDao.update(book);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
