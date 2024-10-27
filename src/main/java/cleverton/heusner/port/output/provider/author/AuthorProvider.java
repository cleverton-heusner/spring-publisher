package cleverton.heusner.port.output.provider.author;

import cleverton.heusner.domain.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorProvider {

    Author register(final Author author);
    Optional<Author> findById(final Long id);
    Optional<Author> findByName(final String name);
    List<Author> findAll();
    void deleteById(final Long id);
}
