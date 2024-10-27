package cleverton.heusner.service.book;

import cleverton.heusner.port.output.provider.book.BookProvider;
import cleverton.heusner.domain.service.book.BookServiceImpl;
import cleverton.heusner.service.ServiceConfiguration;
import cleverton.heusner.port.input.service.author.AuthorService;
import cleverton.heusner.port.input.service.author.AuthorWithBookService;
import cleverton.heusner.port.input.utils.IdFormatterComponent;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class BookServiceTestConfiguration extends ServiceConfiguration {

    protected static final String EXISTING_BOOK_BY_ISBN_MESSAGE = "Livro com ISBN '%s' já existente.";
    protected static final String BOOK_NOT_FOUND_BY_ID_MESSAGE = "Livro com id '%d' não encontrado.";
    protected static final String BOOK_NOT_FOUND_BY_ISBN_MESSAGE = "Livro com ISBN '%s' não encontrado.";
    protected static final String AUTHOR_IN_USE_MESSAGE = "Autor '%s' já vinculado a livro de ISBN '%s'.";

    @Mock
    protected BookProvider bookProvider;

    @Mock
    protected AuthorWithBookService authorWithBookService;

    @Mock
    protected AuthorService authorService;

    @Mock
    protected IdFormatterComponent idFormatterComponent;

    @InjectMocks
    protected BookServiceImpl bookServiceImpl;
}