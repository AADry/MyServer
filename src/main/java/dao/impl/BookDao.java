package dao.impl;

import dao.CrudDao;
import dbconnection.ConnectionManager;
import exception.DaoException;
import model.Author;
import model.Book;
import model.Publisher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao implements CrudDao<Book> {
    Connection connection;

    private Connection getConnection() throws DaoException {
        try {
            return ConnectionManager.getConnection();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public Book get(long id) throws DaoException {
        try {
            connection = getConnection();
            String query = "SELECT book.id, book.title, book.publisher_id, publisher.name, publisher.address FROM public.book  LEFT JOIN publisher ON book.publisher_id = publisher.id WHERE book.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Book book = new Book();
            book.setId(resultSet.getLong(1));
            book.setTitle(resultSet.getString(2));
            Publisher p = new Publisher();
            p.setId(resultSet.getLong(3));
            p.setName(resultSet.getString(4));
            p.setAddress(resultSet.getString(5));
            book.setPublisher(p);
            book.setAuthors(BookAuthorDao.getAuthors(book.getId()));
            return book;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public void save(Book book) throws DaoException {
        try {
            connection = getConnection();
            String selectStatement = "insert into book values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setLong(1, book.getId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setLong(3, book.getPublisher().getId());
            preparedStatement.executeUpdate();
            List<Author> authors = book.getAuthors();
            if (authors != null) {
                List<Long> authorIds = new ArrayList<>();
                for (Author a : authors) {
                    authorIds.add(a.getId());
                }
                String query = "insert into book_author values (?,?)";
                for (Long authorId : authorIds) {
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setLong(1, book.getId());
                    preparedStatement.setLong(2, authorId);
                    preparedStatement.executeUpdate();
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public List<Book> getAll() throws DaoException {
        try {
            ArrayList<Integer> a;
            connection = getConnection();
            String selectStatement = "SELECT book.id, book.title, book.publisher_id, publisher.name, " +
                    "publisher.address FROM public.book " +
                    "LEFT JOIN publisher ON book.publisher_id = publisher.id";
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong(1));
                book.setTitle(resultSet.getString(2));
                Publisher p = new Publisher();
                p.setId(resultSet.getLong(3));
                p.setName(resultSet.getString(4));
                p.setAddress(resultSet.getString(5));
                book.setPublisher(p);
                books.add(book);
                book.setAuthors(BookAuthorDao.getAuthors(book.getId()));
            }
            connection.close();
            return books;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public void update(Book book) throws DaoException {
        try {
            connection = getConnection();
            String sql = "UPDATE public.book SET id=?, title=?, publisher_id=? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, book.getId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setLong(3, book.getPublisher().getId());
            preparedStatement.setLong(4, book.getId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }


    public void delete(Book book) throws DaoException {
        try {
            connection = getConnection();
            String selectStatement = "DELETE FROM public.book WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setLong(1, book.getId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

}
