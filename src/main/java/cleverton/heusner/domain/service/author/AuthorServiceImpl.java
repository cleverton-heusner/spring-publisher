package cleverton.heusner.domain.service.author;

import cleverton.heusner.domain.exception.BusinessException;
import cleverton.heusner.domain.exception.ExistingResourceException;
import cleverton.heusner.domain.exception.ResourceInUseException;
import cleverton.heusner.domain.exception.ResourceNotFoundException;
import cleverton.heusner.domain.model.Author;
import cleverton.heusner.port.input.service.author.AuthorService;
import cleverton.heusner.port.input.utils.IdFormatterComponent;
import cleverton.heusner.port.output.provider.author.AuthorProvider;
import cleverton.heusner.port.shared.MessageComponent;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static cleverton.heusner.adapter.input.constant.business.AuthorBusinessErrorMessage.*;

public class AuthorServiceImpl implements AuthorService {

    @Value("${author.age-of-majority}")
    private int authorAgeOfMajority;

    private final AuthorProvider authorProvider;
    private final MessageComponent messageComponent;
    private final IdFormatterComponent idFormatterComponent;

    public AuthorServiceImpl(final AuthorProvider authorProvider,
                             final MessageComponent messageComponent,
                             final IdFormatterComponent idFormatterComponent) {

        this.authorProvider = authorProvider;
        this.messageComponent = messageComponent;
        this.idFormatterComponent = idFormatterComponent;
    }

    @Override
    public Author findById(final String id) {
        return authorProvider.findById(idFormatterComponent.formatId(id))
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageComponent.getMessage(AUTHOR_NOT_FOUND_BY_NAME_MESSAGE, id)
                ));
    }

    @Override
    public Author register(final Author author) {

        if (author.isBirthDateInFuture()) {
            throw new BusinessException(messageComponent.getMessage(AUTHOR_BIRTH_DATE_IN_FUTURE));
        }

        if (!author.isOfLegalAge(authorAgeOfMajority)) {
            throw new BusinessException(messageComponent.getMessage(
                    AUTHOR_MINOR_OF_AGE,
                    authorAgeOfMajority)
            );
        }

        throwExistingResourceExceptionIfExistingAuthor(author);
        return authorProvider.register(author);
    }

    private void throwExistingResourceExceptionIfExistingAuthor(final Author author) {
        final var foundAuthorOptional = authorProvider.findByName(author.getName().strip());

        if (foundAuthorOptional.isPresent()) {
            throw new ExistingResourceException(messageComponent.getMessage(
                    AUTHOR_EXISTING_MESSAGE,
                    foundAuthorOptional.get().getName())
            );
        }
    }

    @Override
    public Author findByName(final String name) {
        return authorProvider.findByName(name.strip())
                .orElseThrow(() -> new ResourceNotFoundException(
                                messageComponent.getMessage(AUTHOR_NOT_FOUND_BY_NAME_MESSAGE, name)
                        )
                );
    }

    @Override
    public List<Author> findAll() {
        return authorProvider.findAll();
    }

    @Override
    public void deleteById(final String id) {
        final long formattedId = idFormatterComponent.formatId(id);
        final var foundAuthorOptional = authorProvider.findById(formattedId);

        foundAuthorOptional.ifPresentOrElse(
                this::throwResourceInUseExceptionIfAuthorHasBook,
                () -> throwResourceNotFoundException(formattedId)
        );

        authorProvider.deleteById(formattedId);
    }

    private void throwResourceInUseExceptionIfAuthorHasBook(final Author foundAuthor) {
        if (foundAuthor.hasBook()) {
            throw new ResourceInUseException(messageComponent.getMessage(
                    AUTHOR_IN_USE_MESSAGE,
                    foundAuthor.getName(),
                    foundAuthor.getBook().getIsbn()
            ));
        }
    }

    private void throwResourceNotFoundException(final long authorId) {
        throw new ResourceNotFoundException(messageComponent.getMessage(
                AUTHOR_NOT_FOUND_BY_ID_MESSAGE,
                authorId
        ));
    }
}