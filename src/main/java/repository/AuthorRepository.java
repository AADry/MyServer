package repository;

import model.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    Optional<Author> findById(Long aLong);


    Iterable<Author> findAll();


    void delete(Author entity);


    <S extends Author> S save(S entity);
}
