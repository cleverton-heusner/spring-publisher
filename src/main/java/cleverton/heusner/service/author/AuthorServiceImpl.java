package cleverton.heusner.service.author;

import cleverton.heusner.exception.resource.ExistingResourceException;
import cleverton.heusner.exception.resource.ResourceInUseException;
import cleverton.heusner.exception.resource.ResourceNotFoundException;
import cleverton.heusner.model.Author;
import cleverton.heusner.repository.author.AuthorRepository;
import cleverton.heusner.service.idformatter.IdFormatterService;
import cleverton.heusner.service.message.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

import static cleverton.heusner.constant.message.AuthorMessage.*;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final MessageService messageService;
    private final IdFormatterService idFormatterService;

    public AuthorServiceImpl(final AuthorRepository authorRepository,
                             final MessageService messageService,
                             final IdFormatterService idFormatterService) {

        this.authorRepository = authorRepository;
        this.messageService = messageService;
        this.idFormatterService = idFormatterService;
    }

    @Override
    public Author findById(final String id) {
        return authorRepository.findById(idFormatterService.formatId(id))
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.getMessage(AUTHOR_NOT_FOUND_BY_NAME_MESSAGE, id))
                );
    }

    @Override
    public Author register(final Author author) {
        throwExistingResourceExceptionIfExistingAuthor(author);
        return authorRepository.save(author);
    }

    private void throwExistingResourceExceptionIfExistingAuthor(final Author author) {
        final var foundAuthorOptional = authorRepository.findByNameIgnoreCase(author.getName().strip());

        if (foundAuthorOptional.isPresent()) {
            throw new ExistingResourceException(messageService.getMessage(
                    AUTHOR_EXISTING_MESSAGE,
                    foundAuthorOptional.get().getName())
            );
        }
    }

    @Override
    public Author findByName(final String name) {
        return authorRepository.findByNameIgnoreCase(name.strip())
                .orElseThrow(() -> new ResourceNotFoundException(
                                messageService.getMessage(AUTHOR_NOT_FOUND_BY_NAME_MESSAGE, name)
                        )
                );
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public void deleteById(final String id) {
        final long formattedId = idFormatterService.formatId(id);
        final var foundAuthorOptional = authorRepository.findById(formattedId);

        foundAuthorOptional.ifPresentOrElse(
                this::throwResourceInUseExceptionIfAuthorHasBook,
                () -> throwResourceNotFoundException(formattedId)
        );

        authorRepository.deleteById(formattedId);
    }

    private void throwResourceInUseExceptionIfAuthorHasBook(final Author foundAuthor) {
        if (foundAuthor.hasBook()) {
            throw new ResourceInUseException(messageService.getMessage(
                    AUTHOR_IN_USE_MESSAGE,
                    foundAuthor.getName(),
                    foundAuthor.getBook().getIsbn()
            ));
        }
    }

    private void throwResourceNotFoundException(final long authorId) {
        throw new ResourceNotFoundException(messageService.getMessage(
                AUTHOR_NOT_FOUND_BY_ID_MESSAGE,
                authorId
        ));
    }
}