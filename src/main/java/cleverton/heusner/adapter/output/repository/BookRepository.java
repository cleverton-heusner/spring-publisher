package cleverton.heusner.adapter.output.repository;

import cleverton.heusner.adapter.output.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    boolean existsByIsbn(final String isbn);
    Optional<BookEntity> findByIsbn(final String isbn);
    void deleteByIsbn(final String isbn);
}