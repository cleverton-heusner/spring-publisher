package cleverton.heusner.domain.service.author;

import cleverton.heusner.domain.model.Author;
import cleverton.heusner.port.input.service.author.AuthorWithBookService;
import cleverton.heusner.port.output.provider.author.AuthorWithBookProvider;

import java.util.Optional;

public class AuthorWithBookServiceImpl implements AuthorWithBookService {

    private final AuthorWithBookProvider authorWithBookProvider;

    public AuthorWithBookServiceImpl(final AuthorWithBookProvider authorWithBookProvider) {
        this.authorWithBookProvider = authorWithBookProvider;
    }

    @Override
    public Optional<Author> findByName(final String name) {
        return authorWithBookProvider.findByName(name.strip());
    }
}