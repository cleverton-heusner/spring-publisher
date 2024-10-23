package cleverton.heusner.service.book;

import cleverton.heusner.repository.book.BookRepository;
import cleverton.heusner.repository.book.ExistingBookRepository;
import cleverton.heusner.service.idformatter.IdFormatterService;
import cleverton.heusner.service.ServiceConfiguration;
import cleverton.heusner.service.author.AuthorService;
import cleverton.heusner.service.author.AuthorWithBookService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class BookServiceTestConfiguration extends ServiceConfiguration {

    protected static final String EXISTING_BOOK_BY_ISBN_MESSAGE = "Livro com ISBN '%s' já existente.";
    protected static final String BOOK_NOT_FOUND_BY_ID_MESSAGE = "Livro com id '%d' não encontrado.";
    protected static final String BOOK_NOT_FOUND_BY_ISBN_MESSAGE = "Livro com ISBN '%s' não encontrado.";
    protected static final String AUTHOR_IN_USE_MESSAGE = "Autor '%s' já vinculado a livro de ISBN '%s'.";

    @Mock
    protected BookRepository bookRepository;

    @Mock
    protected ExistingBookRepository existingBookRepository;

    @Mock
    protected AuthorWithBookService authorWithBookService;

    @Mock
    protected AuthorService authorService;

    @Mock
    protected IdFormatterService idFormatterService;

    @InjectMocks
    protected BookServiceImpl bookServiceImpl;
}