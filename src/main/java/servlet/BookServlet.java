package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.impl.AuthorDao;
import dao.impl.BookDao;
import exception.DaoException;
import exception.ServiceException;
import model.Book;
import model.Publisher;
import service.BookService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "BookServlet",
        urlPatterns = {"/books/*"}
)
public class BookServlet extends HttpServlet {
    protected BookService bookService = new BookService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String json = bookService.handleGetRequest(req, resp);
            resp.setStatus(200);
            resp.setHeader("Content-Type", "application/json");
            resp.getOutputStream().println(json);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(404);
            resp.setHeader("Content-Type", "text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getOutputStream().println("Please try again");
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Content-Type", "text/html");
        resp.setCharacterEncoding("UTF-8");
        try {
            bookService.handlePostRequest(req, resp);
            resp.setStatus(201);
            resp.getOutputStream().println("Success");
        } catch (ServiceException | IOException e) {
            e.printStackTrace();
            resp.setStatus(400);
            resp.getOutputStream().println("Error");
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Content-Type", "text/html");
        resp.setCharacterEncoding("UTF-8");
        try {
            bookService.handleDeleteRequest(req, resp);
            resp.setStatus(201);
            resp.getOutputStream().println("Success");
        } catch (ServiceException | IOException e) {
            e.printStackTrace();
            resp.setStatus(400);
            resp.getOutputStream().println("Error");
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Content-Type", "text/html");
        resp.setCharacterEncoding("UTF-8");
        try {
            bookService.handlePutRequest(req, resp);
            resp.setStatus(200);
            resp.setHeader("Content-Type", "application/json");
            resp.getOutputStream().println("Success");
        } catch (ServiceException e) {
            e.printStackTrace();
            resp.setStatus(400);
            resp.getOutputStream().println("Error");
        }
    }
}
