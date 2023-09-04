package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exception.ServiceException;
import service.AuthorService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "AuthorServlet",
        urlPatterns = {"/authors/*"}
)
public class AuthorServlet extends HttpServlet {
    protected AuthorService authorService = new AuthorService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String json = authorService.handleGetRequest(req, resp);
            resp.setStatus(200);
            resp.setHeader("Content-Type", "application/json");
            resp.getOutputStream().println(json);
        } catch (ServiceException e) {
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
            authorService.handlePostRequest(req, resp);
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
        try {
            authorService.handleDeleteRequest(req, resp);
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
            authorService.handlePutRequest(req, resp);
            resp.setStatus(200);
            resp.getOutputStream().println("Success");
        } catch (ServiceException e) {
            e.printStackTrace();
            resp.setStatus(400);
            resp.getOutputStream().println("Error");
        }
    }
}
