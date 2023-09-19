package model;

import java.util.List;
public class Author {
    long id;
    String name;
    List<Book> books;

    public Author(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Author() {
    }

    public Author(long id, String name) {
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
