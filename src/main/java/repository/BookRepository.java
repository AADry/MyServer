package repository;

import model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findById(Long aLong);


    Iterable<Book> findAll();


    void delete(Book entity);


    <S extends Book> S save(S entity);
}
