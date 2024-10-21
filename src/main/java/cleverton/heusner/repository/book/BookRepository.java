package cleverton.heusner.repository.book;

import cleverton.heusner.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(final String isbn);
    void deleteByIsbn(final String isbn);
}