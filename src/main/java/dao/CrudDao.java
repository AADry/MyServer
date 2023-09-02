package dao;

import exception.DaoException;

import java.util.List;

public interface CrudDao<T> {

    T get(long id) throws DaoException;

    List<T> getAll() throws DaoException;

    void save(T t) throws DaoException;

    void update(T t) throws DaoException;

    void delete(T t) throws DaoException;
}