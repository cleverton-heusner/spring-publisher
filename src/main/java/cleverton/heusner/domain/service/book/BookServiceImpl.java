package cleverton.heusner.domain.service.book;

import cleverton.heusner.domain.exception.ExistingResourceException;
import cleverton.heusner.domain.exception.ResourceInUseException;
import cleverton.heusner.domain.exception.ResourceNotFoundException;
import cleverton.heusner.domain.model.Author;
import cleverton.heusner.domain.model.Book;
import cleverton.heusner.port.input.service.author.AuthorService;
import cleverton.heusner.port.input.service.author.AuthorWithBookService;
import cleverton.heusner.port.input.service.book.BookService;
import cleverton.heusner.port.input.utils.IdFormatterComponent;
import cleverton.heusner.port.output.provider.book.BookProvider;
import cleverton.heusner.port.shared.MessageComponent;

import java.util.List;

import static cleverton.heusner.adapter.input.constant.business.AuthorBusinessErrorMessage.AUTHOR_IN_USE_MESSAGE;
import static cleverton.heusner.adapter.input.constant.business.AuthorBusinessErrorMessage.AUTHOR_NOT_FOUND_BY_NAME_MESSAGE;
import static cleverton.heusner.adapter.input.constant.business.BookBusinessErrorMessage.*;

public class BookServiceImpl implements BookService {

    private final BookProvider bookProvider;
    private final AuthorWithBookService authorWithBookService;
    private final AuthorService authorService;
    private final MessageComponent messageComponent;
    private final IdFormatterComponent idFormatterComponent;

    public BookServiceImpl(final BookProvider bookProvider,
                           final AuthorWithBookService authorWithBookService,
                           final AuthorService authorService,
                           final MessageComponent messageComponent,
                           final IdFormatterComponent idFormatterComponent) {

        this.bookProvider = bookProvider;
        this.authorWithBookService = authorWithBookService;
        this.authorService = authorService;
        this.messageComponent = messageComponent;
        this.idFormatterComponent = idFormatterComponent;
    }

    @Override
    public Book findById(final String id) {
        return bookProvider.findById(idFormatterComponent.formatId(id))
                .orElseThrow(() -> new ResourceNotFoundException(messageComponent.getMessage(
                        BOOK_NOT_FOUND_BY_ID_MESSAGE,
                        id)
                )
        );
    }

    @Override
    public Book findByIsbn(final String isbn) {
        return bookProvider.findByIsbn(isbn)
                .orElseThrow(
                () -> new ResourceNotFoundException(messageComponent.getMessage(
                        BOOK_NOT_FOUND_BY_ISBN_MESSAGE,
                        isbn)
                )
        );
    }

    @Override
    public Book register(final Book book) {
        final Author foundAuthor = findAuthor(book.getAuthor().getName());
        book.setAuthor(foundAuthor);
        throwResourceInUseExceptionIfAuthorIsInUse(foundAuthor.getName(), book.getIsbn());
        throwExistingResourceExceptionIfExistingBook(book);

        return bookProvider.register(book);
    }

    private void throwExistingResourceExceptionIfExistingBook(final Book book) {
        if (bookProvider.existsByIsbn(book.getIsbn())) {
            throw new ExistingResourceException(messageComponent.getMessage(
                    BOOK_WITH_ISBN_ALREADY_EXISTING_MESSAGE,
                    book.getIsbn())
            );
        }
    }

    private void throwResourceInUseExceptionIfAuthorIsInUse(final String authorName, final String isbnBook) {
        final var firstAuthorWithBookOptional = authorWithBookService.findByName(authorName);

        firstAuthorWithBookOptional.ifPresent(_ -> {
            throw new ResourceInUseException(messageComponent.getMessage(AUTHOR_IN_USE_MESSAGE, authorName, isbnBook));
        });
    }

    private Author findAuthor(final String authorName) {
        final var foundAuthor = authorService.findByName(authorName);

        if (foundAuthor == null) {
            throw new ResourceNotFoundException(messageComponent.getMessage(
                    AUTHOR_NOT_FOUND_BY_NAME_MESSAGE,
                    authorName)
            );
        }

        return foundAuthor;
    }

    @Override
    public List<Book> findAll() {
        return bookProvider.findAll();
    }

    @Override
    public void deleteById(final String id) {
        final long formattedId = idFormatterComponent.formatId(id);
        throwResourceNotFoundExceptionIfExistingBookById(formattedId);
        bookProvider.deleteById(formattedId);
    }

    private void throwResourceNotFoundExceptionIfExistingBookById(final long id) {
        if (!bookProvider.existsById(id)) {
            throw new ResourceNotFoundException(messageComponent.getMessage(
                    BOOK_NOT_FOUND_BY_ID_MESSAGE,
                    id)
            );
        }
    }

    @Override
    public void deleteByIsbn(final String isbn) {
        throwResourceNotFoundExceptionIfExistingBookByIsbn(isbn);
        bookProvider.deleteByIsbn(isbn);
    }

    private void throwResourceNotFoundExceptionIfExistingBookByIsbn(final String isbn) {
        if (!bookProvider.existsByIsbn(isbn)) {
            throw new ResourceNotFoundException(messageComponent.getMessage(
                    BOOK_NOT_FOUND_BY_ISBN_MESSAGE,
                    isbn)
            );
        }
    }
}