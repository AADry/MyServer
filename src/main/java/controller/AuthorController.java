package controller;

import dto.AuthorDto;
import exception.ServiceException;
import model.Author;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import service.AuthorService;

import java.io.IOException;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    protected final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors/{id}")
    public AuthorDto getAuthor(@PathVariable long id) {
        try {
            return authorService.handleGetRequest(id);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Author not found",
                    e);
        }
    }

    @DeleteMapping("/authors/{id}")
    public void deleteAuthor(@PathVariable long id) {
        try {
            authorService.handleDeleteRequest(id);
        } catch (ServiceException | IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Author not found",
                    e);
        }
    }
    public void postAuthor(@RequestBody Author author){
        try {
            authorService.handlePostRequest(author);
        } catch (IOException | ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Unable to create",
                    e);
        }
    }
    public void updateAuthor(@RequestBody Author author){
        try {
            authorService.handlePutRequest(author);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Author not found",
                    e);
        }
    }
}
