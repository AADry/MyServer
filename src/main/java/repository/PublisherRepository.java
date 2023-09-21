package repository;

import model.Publisher;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {

    Optional<Publisher> findById(Long aLong);


    Iterable<Publisher> findAll();


    void delete(Publisher entity);


    <S extends Publisher> S save(S entity);


}
