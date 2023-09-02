package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.impl.AuthorDao;
import exception.DaoException;
import model.Author;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpRetryException;

@WebServlet(
        name = "AuthorServlet",
        urlPatterns = {"/authors/*"}
)
public class AuthorServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        AuthorDao authorDao = new AuthorDao();
        try {
            Author author = authorDao.get(id);
            String json = GSON.toJson(author);
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
        AuthorDao authorDao = new AuthorDao();
        Author author = new Author(id, name);
        try {
            authorDao.save(author);
            resp.setStatus(201);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = String.valueOf(req.getParameter("name"));
        AuthorDao authorDao = new AuthorDao();
        Author author = new Author(id, name);
        try {
            authorDao.delete(author);
            resp.setStatus(200);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = String.valueOf(req.getParameter("name"));
        AuthorDao authorDao = new AuthorDao();
        Author author = new Author(id, name);
        try {
            authorDao.update(author);
            resp.setStatus(200);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
