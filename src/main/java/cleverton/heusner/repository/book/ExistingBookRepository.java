package cleverton.heusner.repository.book;

import cleverton.heusner.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExistingBookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(final String isbn);
}