package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.impl.PublisherDao;
import exception.DaoException;
import model.Publisher;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpRetryException;

@WebServlet(
        name = "PublisherServlet",
        urlPatterns = {"/publishers/*"}
)
public class PublisherServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        PublisherDao publisherDao = new PublisherDao();
        try {
            Publisher publisher = publisherDao.get(id);
            String json = GSON.toJson(publisher);
            resp.setStatus(200);
            resp.setHeader("Content-Type", "application/json");
            resp.getOutputStream().println(json);
        } catch (DaoException e) {
            throw new HttpRetryException(e.getMessage(), 404);
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = String.valueOf(req.getParameter("name"));
        String address = String.valueOf(req.getParameter("address"));
        PublisherDao publisherDao = new PublisherDao();
        Publisher publisher = new Publisher(id, name, address);
        try {
            publisherDao.save(publisher);
            resp.setStatus(200);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = String.valueOf(req.getParameter("name"));
        String address = String.valueOf(req.getParameter("address"));
        PublisherDao publisherDao = new PublisherDao();
        Publisher publisher = new Publisher(id, name, address);
        try {
            publisherDao.delete(publisher);
            resp.setStatus(200);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = String.valueOf(req.getParameter("name"));
        String address = String.valueOf(req.getParameter("address"));
        PublisherDao publisherDao = new PublisherDao();
        Publisher publisher = new Publisher(id, name, address);
        try {
            publisherDao.update(publisher);
            resp.setStatus(200);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
