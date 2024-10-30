package cleverton.heusner.port.input.service.book;

import cleverton.heusner.domain.model.Book;

import java.util.List;

public interface BookService {
    Book findById(final String id);

    Book findByIsbn(final String isbn);

    Book register(final Book book);

    List<Book> findAll();

    void deleteById(final String id);

    void deleteByIsbn(final String isbn);
}