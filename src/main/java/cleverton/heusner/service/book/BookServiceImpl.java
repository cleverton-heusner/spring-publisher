package cleverton.heusner.service.book;

import cleverton.heusner.exception.resource.ExistingResourceException;
import cleverton.heusner.exception.resource.ResourceInUseException;
import cleverton.heusner.exception.resource.ResourceNotFoundException;
import cleverton.heusner.model.Author;
import cleverton.heusner.model.Book;
import cleverton.heusner.repository.book.BookRepository;
import cleverton.heusner.repository.book.ExistingBookRepository;
import cleverton.heusner.service.idformatter.IdFormatterService;
import cleverton.heusner.service.author.AuthorService;
import cleverton.heusner.service.author.AuthorWithBookService;
import cleverton.heusner.service.message.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cleverton.heusner.constant.message.AuthorMessage.AUTHOR_IN_USE_MESSAGE;
import static cleverton.heusner.constant.message.AuthorMessage.AUTHOR_NOT_FOUND_BY_NAME_MESSAGE;
import static cleverton.heusner.constant.message.BookMessage.*;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ExistingBookRepository existingBookRepository;
    private final AuthorWithBookService authorWithBookService;
    private final AuthorService authorService;
    private final MessageService messageService;
    private final IdFormatterService idFormatterService;

    public BookServiceImpl(final BookRepository bookRepository,
                           final ExistingBookRepository existingBookRepository,
                           final AuthorWithBookService authorWithBookService,
                           final AuthorService authorService,
                           final MessageService messageService,
                           final IdFormatterService idFormatterService) {

        this.bookRepository = bookRepository;
        this.existingBookRepository = existingBookRepository;
        this.authorWithBookService = authorWithBookService;
        this.authorService = authorService;
        this.messageService = messageService;
        this.idFormatterService = idFormatterService;
    }

    @Override
    public Book findById(final String id) {
        return bookRepository.findById(idFormatterService.formatId(id))
                .orElseThrow(() -> new ResourceNotFoundException(messageService.getMessage(
                        BOOK_NOT_FOUND_BY_ID_MESSAGE,
                        id)
                )
        );
    }

    @Override
    public Book findByIsbn(final String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(
                () -> new ResourceNotFoundException(messageService.getMessage(
                        BOOK_NOT_FOUND_BY_ISBN_MESSAGE,
                        isbn)
                )
        );
    }

    @Override
    public Book register(final Book book) {
        final var foundAuthor = findAuthor(book.getAuthor());
        book.setAuthor(foundAuthor);
        throwResourceInUseExceptionIfAuthorIsInUse(foundAuthor.getName());
        throwExistingResourceExceptionIfBookAlreadyExists(book);

        return bookRepository.save(book);
    }

    private void throwExistingResourceExceptionIfBookAlreadyExists(final Book book) {
        if (existingBookRepository.existsByIsbn(book.getIsbn())) {
            throw new ExistingResourceException(messageService.getMessage(
                    BOOK_WITH_ISBN_ALREADY_EXISTING_MESSAGE,
                    book.getIsbn())
            );
        }
    }

    private void throwResourceInUseExceptionIfAuthorIsInUse(final String authorName) {
        final var firstAuthorWithBookOptional = authorWithBookService.findFirstByNameAndWithBook(authorName);

        firstAuthorWithBookOptional.ifPresent(_ -> {
            throw new ResourceInUseException(messageService.getMessage(
                    AUTHOR_IN_USE_MESSAGE,
                    firstAuthorWithBookOptional.get().getName(),
                    firstAuthorWithBookOptional.get().getBook().getIsbn())
            );
        });
    }

    private Author findAuthor(final Author author) {
        final var foundAuthor = authorService.findByName(author.getName());

        if (foundAuthor == null) {
            throw new ResourceNotFoundException(messageService.getMessage(
                    AUTHOR_NOT_FOUND_BY_NAME_MESSAGE,
                    author.getName())
            );
        }

        return foundAuthor;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteById(final String id) {
        final long formattedId = idFormatterService.formatId(id);
        throwResourceNotFoundExceptionIfBookAlreadyExists(formattedId);
        bookRepository.deleteById(formattedId);
    }

    private void throwResourceNotFoundExceptionIfBookAlreadyExists(final long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException(messageService.getMessage(
                    BOOK_NOT_FOUND_BY_ID_MESSAGE,
                    id)
            );
        }
    }

    @Transactional
    @Override
    public void deleteByIsbn(final String isbn) {
        existsByIsbn(isbn);
        bookRepository.deleteByIsbn(isbn);
    }

    private void existsByIsbn(final String isbn) {
        if (!existingBookRepository.existsByIsbn(isbn)) {
            throw new ResourceNotFoundException(messageService.getMessage(
                    BOOK_NOT_FOUND_BY_ISBN_MESSAGE,
                    isbn)
            );
        }
    }
}