package cleverton.heusner.adapter.output.provider.author;

import cleverton.heusner.adapter.output.mapper.AuthorEntityMapper;
import cleverton.heusner.adapter.output.repository.AuthorRepository;
import cleverton.heusner.domain.model.Author;
import cleverton.heusner.adapter.output.entity.AuthorEntity;
import cleverton.heusner.port.output.provider.author.AuthorProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorProviderImpl implements AuthorProvider {

    private final AuthorEntityMapper authorEntityMapper;
    private final AuthorRepository authorRepository;

    public AuthorProviderImpl(final AuthorEntityMapper authorEntityMapper,
                              final AuthorRepository authorRepository) {
        this.authorEntityMapper = authorEntityMapper;
        this.authorRepository = authorRepository;
    }

    @Override
    public Author register(final Author author){
        final AuthorEntity savedAuthor = authorRepository.save(authorEntityMapper.toEntity(author));
        return authorEntityMapper.toModel(savedAuthor);
    }

    public Optional<Author> findById(final Long id) {
        final Optional<AuthorEntity> authorFoundOptional = authorRepository.findById(id);
        return authorFoundOptional.map(authorEntityMapper::toModel);
    }

    public Optional<Author> findByName(final String name) {
        final Optional<AuthorEntity> authorFoundOptional = authorRepository.findByNameIgnoreCase(name);
        return authorFoundOptional.map(authorEntityMapper::toModel);
    }

    public List<Author> findAll() {
        return authorRepository.findAll().stream().map(authorEntityMapper::toModel).toList();
    }

    public void deleteById(final Long id) {
        authorRepository.deleteById(id);
    }
}