package cleverton.heusner.service.book;

import cleverton.heusner.model.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookService {
    Book findById(final String id);

    Book findByIsbn(final String isbn);

    Book register(final Book book);

    List<Book> findAll();

    void deleteById(final String id);

    @Transactional
    void deleteByIsbn(final String isbn);
}
