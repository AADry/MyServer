package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Book;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(
        name = "BookServlet",
        urlPatterns = {"/books/*"}
)
public class BookServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        Long id = Long.valueOf(req.getParameter("book"));
        Map<Long, Book> books = new HashMap<>();

            books.put(1L, new Book(1L, "first book"));
            books.put(2L, new Book(2L, "second book"));

        String json = GSON.toJson(books.get(id));

        resp.setStatus(200);
        resp.setHeader("Content-Type", "application/json");
        try{
            resp.getOutputStream().println(json);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
