package controller;

import com.google.common.base.Preconditions;
import dto.BookDto;
import exception.ServiceException;
import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import service.AuthorService;
import service.BookService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/books")
public class BookController {
    protected final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/{id}")
    public BookDto getBook(@PathVariable long id) {
        try {
            return bookService.handleGetRequest(id);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Book not found",
                    e);
        }
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable long id) {
        try {
            bookService.handleDeleteRequest(id);
        } catch (ServiceException | IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Book not found",
                    e);
        }
    }

    @PostMapping
    public void postBook(@RequestBody Book book) {
        try {
            bookService.handlePostRequest(book);
        } catch (ServiceException | IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Book already presented",
                    e);
        }
    }

    @PutMapping
    public void updateBook(@RequestBody Book book) {
        try {
            bookService.handlePutRequest(book);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Book not found",
                    e);
        }
    }
}
