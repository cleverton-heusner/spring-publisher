package cleverton.heusner.service.author;

import cleverton.heusner.model.Author;
import cleverton.heusner.repository.author.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorWithBookServiceImpl implements AuthorWithBookService {

    private final AuthorRepository authorRepository;

    public AuthorWithBookServiceImpl(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Optional<Author> findFirstByNameAndWithBook(final String name) {
        return authorRepository.findByNameIgnoreCaseAndBookIsNotNull(name.strip());
    }
}