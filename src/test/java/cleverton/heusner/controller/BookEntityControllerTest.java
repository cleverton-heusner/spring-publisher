package cleverton.heusner.controller;

import cleverton.heusner.adapter.input.request.BookCreationRequest;
import cleverton.heusner.adapter.input.response.BookWithAuthorResponse;
import cleverton.heusner.domain.model.Book;
import io.restassured.common.mapper.TypeRef;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class BookEntityControllerTest extends BookEntityControllerTestConfiguration {

    @Test
    public void given_bookIsExisting_when_booksIsQueriedById_then_okResponseIsReturned() {

        // Arrange
        final var expectedBook = Instancio.create(Book.class);
        final var expectedBookWithAuthorResponse = registerWithAuthorResponse(expectedBook);
        final String bookId = String.valueOf(expectedBook.getId());

        when(bookService.findById(bookId)).thenReturn(expectedBook);
        when(bookWithAuthorResponseMapper.toResponse(expectedBook)).thenReturn(expectedBookWithAuthorResponse);

        // Act
        final var response = given()
                .accept(APPLICATION_JSON_VALUE)
                .when()
                .get("/books/id/{id}", expectedBook.getId());
        final var actualBookWithAuthorResponse = response.as(BookWithAuthorResponse.class);

        // Assert
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.contentType()).isEqualTo(APPLICATION_JSON_VALUE);
        assertThat(actualBookWithAuthorResponse).isEqualTo(expectedBookWithAuthorResponse);

        verify(bookService).findById(bookId);
        verify(bookWithAuthorResponseMapper).toResponse(expectedBook);
    }

    @Test
    public void given_bookIsExisting_when_booksIsQueriedByIsbn_then_okResponseIsReturned() {

        // Arrange
        final var expectedBook = registerWithValidIsbn();
        final var expectedBookWithAuthorResponse = registerWithAuthorResponse(expectedBook);

        when(bookService.findByIsbn(expectedBook.getIsbn())).thenReturn(expectedBook);
        when(bookWithAuthorResponseMapper.toResponse(expectedBook)).thenReturn(expectedBookWithAuthorResponse);

        // Act
        final var response = given()
                .accept(APPLICATION_JSON_VALUE)
                .when()
                .get("/books/isbn/{isbn}", expectedBook.getIsbn());
        final var actualBookWithAuthorResponse = response.as(BookWithAuthorResponse.class);

        // Assert
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.contentType()).isEqualTo(APPLICATION_JSON_VALUE);
        assertThat(actualBookWithAuthorResponse).isEqualTo(expectedBookWithAuthorResponse);

        verify(bookService).findByIsbn(expectedBook.getIsbn());
        verify(bookWithAuthorResponseMapper).toResponse(expectedBook);
    }

    @Test
    public void given_booksAreExisting_when_booksAreListed_then_okResponseIsReturned() {

        // Arrange
        final byte expectedBooksSize = 2;
        final var expectedBooks = Instancio.ofList(Book.class).size(expectedBooksSize).create();
        final var expectedBookResponses = expectedBooks.stream()
                .map(this::registerWithAuthorResponse)
                .toList();

        IntStream.range(0, expectedBooks.size())
                .forEach(i -> when(bookWithAuthorResponseMapper.toResponse(expectedBooks.get(i)))
                        .thenReturn(expectedBookResponses.get(i))
                );
        when(bookService.findAll()).thenReturn(expectedBooks);

        // Act
        final var response = given()
                .accept(APPLICATION_JSON_VALUE)
                .when()
                .get("/books");
        final var actualBookResponses = response.as(new TypeRef<List<BookWithAuthorResponse>>() {});

        // Assert
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.contentType()).isEqualTo(APPLICATION_JSON_VALUE);
        assertThat(actualBookResponses).isEqualTo(expectedBookResponses);

        expectedBooks.forEach(book -> verify(bookWithAuthorResponseMapper).toResponse(book));
        verify(bookService).findAll();
    }

    @Test
    public void given_bookIsNew_and_authorIsAvailable_when_bookIsCreated_then_createResponseIsReturned() {

        // Arrange
        final var bookCreationRequest = Instancio.of(BookCreationRequest.class)
                .set(Select.field(BookCreationRequest::isbn), VALID_ISBN)
                .create();
        final var expectedBook = registerWithValidIsbn();
        final var expectedBookWithAuthorResponse = registerWithAuthorResponse(expectedBook);

        when(bookCreationRequestMapper.toModel(bookCreationRequest)).thenReturn(expectedBook);
        when(bookService.register(expectedBook)).thenReturn(expectedBook);
        when(bookWithAuthorResponseMapper.toResponse(expectedBook)).thenReturn(expectedBookWithAuthorResponse);

        // Act
        final var response = given()
                .contentType(APPLICATION_JSON_VALUE)
                .body(bookCreationRequest)
                .when()
                .post("/books");
        final var actualBookWithAuthorResponse = response.as(BookWithAuthorResponse.class);

        // Assert
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
        assertThat(response.contentType()).isEqualTo(APPLICATION_JSON_VALUE);
        assertThat(actualBookWithAuthorResponse).isEqualTo(expectedBookWithAuthorResponse);

        verify(bookCreationRequestMapper).toModel(bookCreationRequest);
        verify(bookWithAuthorResponseMapper).toResponse(expectedBook);
        verify(bookService).register(expectedBook);
    }

    @Test
    public void given_bookIsExisting_when_bookIsDeletedById_then_noContentResponseIsReturned() {

        // Arrange
        final String bookId = "1";

        // Act
        final var response = given()
                .when()
                .delete("/books/id/{id}", bookId);

        // Assert
        assertThat(response.statusCode()).isEqualTo(NO_CONTENT.value());
        verify(bookService).deleteById(bookId);
    }

    @Test
    public void given_bookIsExisting_when_bookIsDeletedByIsbn_then_noContentResponseIsReturned() {

        // Act
        final var response = given()
                .when()
                .delete("/books/isbn/{isbn}", VALID_ISBN);

        // Assert
        assertThat(response.statusCode()).isEqualTo(NO_CONTENT.value());
        verify(bookService).deleteByIsbn(VALID_ISBN);
    }
}