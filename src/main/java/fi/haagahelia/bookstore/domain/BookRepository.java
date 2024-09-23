package fi.haagahelia.bookstore.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// the annotation is here to use queris from the CrudRepository by using REST API
// requirement: add @Param annotation for the parameters
@RepositoryRestResource
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByYears(@Param("year") int years);

    List<Book> findByTitle(@Param("name") String title);
}
