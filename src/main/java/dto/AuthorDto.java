package dto;

import model.Book;

import java.util.List;

public class AuthorDto {
    long id;
    String name;
    List<Book> books;

    public AuthorDto(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public AuthorDto() {
    }

    public AuthorDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
