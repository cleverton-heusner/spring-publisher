package cleverton.heusner.adapter.output.provider.author;

import cleverton.heusner.adapter.output.mapper.AuthorEntityMapper;
import cleverton.heusner.adapter.output.repository.AuthorWithBookRepository;
import cleverton.heusner.domain.model.Author;
import cleverton.heusner.port.output.provider.author.AuthorWithBookProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorWithBookProviderImpl implements AuthorWithBookProvider {

    private final AuthorWithBookRepository authorWithBookRepository;
    private final AuthorEntityMapper authorEntityMapper;

    public AuthorWithBookProviderImpl(final AuthorWithBookRepository authorWithBookRepository,
                                      final AuthorEntityMapper authorEntityMapper) {
        this.authorWithBookRepository = authorWithBookRepository;
        this.authorEntityMapper = authorEntityMapper;
    }

    @Override
    public Optional<Author> findByName(final String name) {
        return authorWithBookRepository.findByNameIgnoreCaseAndBookIsNotNull(name)
                .map(authorEntityMapper::toModel);
    }
}