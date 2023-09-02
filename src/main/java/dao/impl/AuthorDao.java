package dao.impl;

import dao.CrudDao;
import dbconnection.ConnectionManager;
import exception.DaoException;
import model.Author;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao implements CrudDao<Author> {
    Connection connection;


    private Connection getConnection() throws DaoException {
        try {
            return ConnectionManager.getConnection();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public Author get(long id) throws DaoException {
        try {
            connection = getConnection();

            String query = "SELECT * FROM public.author WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Author author = new Author();
            author.setId(resultSet.getLong(1));
            author.setName(resultSet.getString(2));
            author.setBooks(BookAuthorDao.getBooks(author.getId()));
            return author;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public void save(Author author) throws DaoException {
        try {
            connection = getConnection();
            String selectStatement = "insert into author values (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setLong(1, author.getId());
            preparedStatement.setString(2, author.getName());
            preparedStatement.executeUpdate();
            List<Book> books = author.getBooks();
            if (books != null) {
                List<Long> bookIds = new ArrayList<>();
                for (Book book : books) {
                    bookIds.add(book.getId());
                }
                String query = "insert into book_author values (?,?)";
                for (Long bookId : bookIds) {
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setLong(1, bookId);
                    preparedStatement.setLong(2, author.getId());
                    preparedStatement.executeUpdate();
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public List<Author> getAll() throws DaoException {
        try {
            connection = getConnection();
            String selectStatement = "Select * from public.author";
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Author> authors = new ArrayList<>();
            while (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getLong(1));
                author.setName(resultSet.getString(2));
                authors.add(author);
                author.setBooks(BookAuthorDao.getBooks(author.getId()));
            }
            connection.close();
            return authors;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public void update(Author author) throws DaoException {
        try {
            connection = getConnection();
            String sql = "UPDATE public.author SET id=?, name=? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, author.getId());
            preparedStatement.setString(2, author.getName());
            preparedStatement.setLong(3, author.getId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public void delete(Author author) throws DaoException {
        try {
            connection = getConnection();
            String selectStatement = "DELETE FROM public.author WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setLong(1, author.getId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }
}