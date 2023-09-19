package dto;

import model.Author;
import model.Publisher;

import java.util.List;

public class BookDto {
    private String title;
    private List<Author> authors;
    private Publisher publisher;
    private long id;
    private int authorsNum;

    public BookDto() {

    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
        authorsNum = authors.size();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
