package cleverton.heusner.port.output.provider.author;

import cleverton.heusner.domain.model.Author;

import java.util.Optional;

public interface AuthorWithBookProvider {
    Optional<Author> findByName(String name);
}
