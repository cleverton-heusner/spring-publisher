package cleverton.heusner.controller;

import cleverton.heusner.dto.book.BookWithoutAuthorResponse;
import cleverton.heusner.mapper.book.BookCreationRequestMapper;
import cleverton.heusner.mapper.book.BookResponseMapper;
import cleverton.heusner.dto.author.AuthorWithoutBookResponse;
import cleverton.heusner.model.Book;
import cleverton.heusner.dto.book.BookWithAuthorResponse;
import cleverton.heusner.service.book.BookService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.instancio.Select.field;

@ExtendWith(MockitoExtension.class)
public class BookControllerTestConfiguration {

    private static final String AUTHOR_WITHOUT_BOOK_RESPONSE_ID = "id";
    private static final String AUTHOR_WITHOUT_BOOK_RESPONSE_NAME = "name";

    protected static final String VALID_ISBN = "9780306406157";

    @Mock
    protected BookService bookService;

    @Mock
    protected BookResponseMapper bookResponseMapper;

    @Mock
    protected BookCreationRequestMapper bookCreationRequestMapper;

    @InjectMocks
    protected BookController bookController;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(bookController);
    }

    protected Book registerWithValidIsbn() {
        return Instancio.of(Book.class)
                .set(Select.field(Book::getIsbn), VALID_ISBN)
                .create();
    }

    protected BookWithAuthorResponse registerWithAuthorResponse(final Book book) {
        return Instancio.of(BookWithAuthorResponse.class)
                .set(field(BookWithoutAuthorResponse.class, "id"), book.getId())
                .set(field(BookWithoutAuthorResponse.class, "isbn"), book.getIsbn())
                .set(field(BookWithoutAuthorResponse.class, "title"), book.getTitle())
                .set(field(
                        AuthorWithoutBookResponse.class,
                        AUTHOR_WITHOUT_BOOK_RESPONSE_ID
                ), book.getAuthor().getId())
                .set(field(
                        AuthorWithoutBookResponse.class,
                        AUTHOR_WITHOUT_BOOK_RESPONSE_NAME
                ), book.getAuthor().getName())
                .create();
    }
}