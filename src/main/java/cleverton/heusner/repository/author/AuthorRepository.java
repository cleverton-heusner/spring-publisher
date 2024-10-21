package cleverton.heusner.repository.author;

import cleverton.heusner.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByNameIgnoreCase(final String name);
    Optional<Author> findByNameIgnoreCaseAndBookIsNotNull(final String name);
}