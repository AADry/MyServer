package dao.impl;

import dao.CrudDao;
import dbconnection.ConnectionManager;
import exception.DaoException;
import model.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublisherDao implements CrudDao<Publisher> {
    Connection connection;


    private Connection getConnection() throws DaoException {
        try {
            return ConnectionManager.getConnection();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public Publisher get(long id) throws DaoException {
        try {
            Publisher publisher = new Publisher();
            String query = "SELECT * FROM public.publisher WHERE id = ?";
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            publisher.setId(Long.parseLong(resultSet.getString("id")));
            publisher.setName(resultSet.getString("name"));
            return publisher;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public List<Publisher> getAll() throws DaoException {
        try {
            List<Publisher> list = new ArrayList<>();
            String sql = "SELECT * FROM public.publisher";
            connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Publisher p = new Publisher();
                p.setId(resultSet.getLong("id"));
                p.setName(resultSet.getString("name"));
                p.setAddress(resultSet.getString("address"));
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public void update(Publisher publisher) throws DaoException {
        try {
            connection = getConnection();
            String sql = "UPDATE public.publisher SET id=?, name=?, address=? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, publisher.getId());
            preparedStatement.setString(2, publisher.getName());
            preparedStatement.setString(3, publisher.getAddress());
            preparedStatement.setLong(4, publisher.getId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public void save(Publisher publisher) throws DaoException {
        try {
            connection = getConnection();
            String selectStatement = "insert into publisher values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setLong(1, publisher.getId());
            preparedStatement.setString(2, publisher.getName());
            preparedStatement.setString(3, publisher.getAddress());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public void delete(Publisher publisher) throws DaoException {
        try {
            connection = getConnection();
            String sql = "DELETE FROM public.publisher WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, publisher.getId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }
}