package cleverton.heusner.port.input.service.author;

import cleverton.heusner.domain.model.Author;

import java.util.Optional;

public interface AuthorWithBookService {
    Optional<Author> findByName(final String name);
}
