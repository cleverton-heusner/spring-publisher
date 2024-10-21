package cleverton.heusner.service.author;

import cleverton.heusner.model.Author;

import java.util.Optional;

public interface AuthorWithBookService {
    Optional<Author> findFirstByNameAndWithBook(final String name);
}
