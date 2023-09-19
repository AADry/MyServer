package servlet;

import exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import service.PublisherService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "PublisherServlet",
        urlPatterns = {"/publishers/*"}
)
public class PublisherServlet extends HttpServlet {
    @Autowired
    PublisherService publisherService;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String json = publisherService.handleGetRequest(req, resp);
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
            publisherService.handlePostRequest(req, resp);
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
            publisherService.handleDeleteRequest(req, resp);
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
        try {
            publisherService.handlePutRequest(req, resp);
            resp.setStatus(201);
            resp.getOutputStream().println("Success");
        } catch (ServiceException | IOException e) {
            e.printStackTrace();
            resp.setStatus(400);
            resp.getOutputStream().println("Error");
        }
    }
}
