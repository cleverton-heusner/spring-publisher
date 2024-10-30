package cleverton.heusner.port.input.service.author;

import cleverton.heusner.domain.model.Author;

import java.util.List;

public interface AuthorService {
    Author findById(final String id);

    Author findByName(final String name);

    Author register(final Author author);

    List<Author> findAll();

    void deleteById(final String id);
}
