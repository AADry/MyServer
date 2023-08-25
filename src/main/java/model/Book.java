package model;

public class Book {
    private String title;
    private String author;
    private long id;

    public Book(long id,String title) {
        this.title = title;
        this.id = id;
    }
}
