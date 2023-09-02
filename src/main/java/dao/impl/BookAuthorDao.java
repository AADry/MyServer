package dao.impl;

import dbconnection.ConnectionManager;
import exception.DaoException;
import model.Author;
import model.Book;
import model.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookAuthorDao {
    static Connection connection;

    private BookAuthorDao() {
    }

    private static Connection getConnection() throws DaoException {
        try {
            return ConnectionManager.getConnection();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public static List<Book> getBooks(Long authorId) throws DaoException {
        try {
            connection = getConnection();
            String query = "SELECT book_author.author_id, book_author.book_id, book.title, " +
                    "book.publisher_id, publisher.name,publisher.address " +
                    "FROM public.book_author " +
                    "LEFT JOIN public.book ON book_author.book_id = book.id " +
                    "LEFT JOIN public.publisher ON book.publisher_id = publisher.id " +
                    "WHERE book_author.author_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong(2));
                book.setTitle(resultSet.getString(3));
                Publisher p = new Publisher();
                p.setId(resultSet.getLong(4));
                p.setName(resultSet.getString(5));
                p.setAddress(resultSet.getString(6));
                book.setPublisher(p);
                books.add(book);
            }
            connection.close();
            return books;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public static List<Author> getAuthors(Long bookId) throws DaoException {
        try {
            connection = getConnection();
            String query = "SELECT book_author.author_id, book_author.book_id,author.id, author.name " +
                    "FROM public.book_author " +
                    "LEFT JOIN public.author ON book_author.author_id = author.id " +
                    "WHERE book_author.book_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Author> authors = new ArrayList<>();
            while (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getLong(3));
                author.setName(resultSet.getString(4));
                authors.add(author);
            }
            connection.close();
            return authors;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }
}
