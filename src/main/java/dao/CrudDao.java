package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    Optional<T> get(long id) throws SQLException;

    List<T> getAll();

    void save(T t);

    void update(T t, String[] params);

    void delete(T t);
}
