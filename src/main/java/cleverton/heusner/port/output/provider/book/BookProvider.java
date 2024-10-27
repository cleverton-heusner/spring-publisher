package cleverton.heusner.port.output.provider.book;

import cleverton.heusner.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookProvider {

    Book register(final Book book);
    Optional<Book> findById(final Long id);
    Optional<Book> findByIsbn(final String isbn);
    List<Book> findAll();
    Boolean existsById(final Long id);
    Boolean existsByIsbn(final String isbn);
    void deleteById(final Long id);
    void deleteByIsbn(final String isbn);
}
