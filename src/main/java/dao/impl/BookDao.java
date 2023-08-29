package dao.impl;

import dao.CrudDao;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BookDao implements CrudDao<Book> {

    @Override
    public Optional<Book> get(long id) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        String selectStatement = "SELECT * FROM public.books WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
        preparedStatement.setLong(1,1);
        ResultSet resultSet = preparedStatement.executeQuery();
        //resultSet.
        return Optional.empty();
    }

    @Override
    public List getAll() {
        return null;
    }

    public void save(Book o) {

    }

    @Override
    public void update(Book book, String[] params) {

    }

    @Override
    public void delete(Book book) {

    }

}
