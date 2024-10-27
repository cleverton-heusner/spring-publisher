package cleverton.heusner.service.book;

import cleverton.heusner.domain.model.Author;
import cleverton.heusner.domain.model.Book;
import cleverton.heusner.domain.exception.ExistingResourceException;
import cleverton.heusner.domain.exception.ResourceInUseException;
import cleverton.heusner.domain.exception.ResourceNotFoundException;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class BookEntityServiceImplTest extends BookServiceTestConfiguration {

    @Test
    void when_existingBookIsRequestedById_then_bookIsRetrieved() {

        // Arrange
        final long existingBookId = 1;
        final var expectedBook = Instancio.create(Book.class);

        when(idFormatterComponent.formatId(String.valueOf(existingBookId))).thenReturn(existingBookId);
        when(bookProvider.findById(existingBookId)).thenReturn(Optional.of(expectedBook));

        // Act
        final Book actualBook = bookServiceImpl.findById(String.valueOf(existingBookId));

        // Assert
        assertThat(actualBook).isEqualTo(expectedBook);

        verify(idFormatterComponent).formatId(String.valueOf(existingBookId));
        verify(bookProvider).findById(existingBookId);
    }

    @Test
    void when_existingBookIsRequestedByIsbn_then_bookIsRetrieved() {

        // Arrange
        final String existingBookIsbn = "9780306406157";
        final var expectedBook = Instancio.create(Book.class);

        when(bookProvider.findByIsbn(existingBookIsbn)).thenReturn(Optional.of(expectedBook));

        // Act
        final Book actualBook = bookServiceImpl.findByIsbn(existingBookIsbn);

        // Assert
        assertThat(actualBook).isEqualTo(expectedBook);
        verify(bookProvider).findByIsbn(existingBookIsbn);
    }

    @Test
    void when_bookCreationIsRequested_and_nonExistingBook_and_existingAuthorWithoutBook_then_bookIsCreated() {

        // Arrange
        final var expectedBook = Instancio.create(Book.class);
        final var expectedAuthor = expectedBook.getAuthor();

        when(authorService.findByName(expectedAuthor.getName())).thenReturn(expectedAuthor);
        when(authorWithBookService.findByName(expectedAuthor.getName())).thenReturn(Optional.empty());
        when(bookProvider.existsByIsbn(expectedBook.getIsbn())).thenReturn(false);
        when(bookProvider.register(expectedBook)).thenReturn(expectedBook);

        // Act
        final Book actualBook = bookServiceImpl.register(expectedBook);

        // Assert
        assertThat(actualBook).isEqualTo(expectedBook);

        verify(authorService).findByName(expectedAuthor.getName());
        verify(authorWithBookService).findByName(expectedAuthor.getName());
        verify(bookProvider).existsByIsbn(expectedBook.getIsbn());
        verify(bookProvider).register(expectedBook);
    }

    @Test
    void when_booksListingIsRequested_and_existingBooks_then_booksAreListed() {

        // Arrange
        final byte booksSize = 2;
        final var expectedBooks = Instancio.ofList(Book.class).size(booksSize).create();

        when(bookProvider.findAll()).thenReturn(expectedBooks);

        // Act
        final List<Book> actualBooks = bookServiceImpl.findAll();

        // Assert
        assertThat(actualBooks).isEqualTo(expectedBooks);
        verify(bookProvider).findAll();
    }

    @Test
    void when_booksListingIsRequested_and_nonExistingBooks_then_emptyListIsReturned() {

        // Arrange
        final List<Book> expectedBooks = Collections.emptyList();

        when(bookProvider.findAll()).thenReturn(expectedBooks);

        // Act
        final List<Book> actualBooks = bookServiceImpl.findAll();

        // Assert
        assertThat(actualBooks).isEqualTo(expectedBooks);
        verify(bookProvider).findAll();
    }

    @Test
    void when_bookDeletionByIdIsRequested_and_existingBook_then_bookIsDeleted() {

        // Arrange
        final long bookId = 1;

        when(idFormatterComponent.formatId(String.valueOf(bookId))).thenReturn(bookId);
        when(bookProvider.existsById(bookId)).thenReturn(true);
        doNothing().when(bookProvider).deleteById(bookId);

        // Act
        bookServiceImpl.deleteById(String.valueOf(bookId));

        // Assert
        verify(idFormatterComponent).formatId(String.valueOf(bookId));
        verify(bookProvider).existsById(bookId);
        verify(bookProvider).deleteById(bookId);
    }

    @Test
    void when_bookDeletionByIdIsbnRequested_and_existingBook_then_bookIsDeleted() {

        // Arrange
        final String isbn = "9780306406157";

        when(bookProvider.existsByIsbn(isbn)).thenReturn(true);
        doNothing().when(bookProvider).deleteByIsbn(isbn);

        // Act
        bookServiceImpl.deleteByIsbn(isbn);

        // Assert
        verify(bookProvider).existsByIsbn(isbn);
        verify(bookProvider).deleteByIsbn(isbn);
    }

    @Test
    void when_nonExistingBookIsRequestedById_then_throwResourceNotFoundException() {

        // Arrange
        final long nonExistingBookId = 1;
        final String expectedBookNotFoundByIdMessage = String.format(BOOK_NOT_FOUND_BY_ID_MESSAGE, nonExistingBookId);

        when(mockErrorMessage()).thenReturn(expectedBookNotFoundByIdMessage);
        when(idFormatterComponent.formatId(String.valueOf(nonExistingBookId))).thenReturn(nonExistingBookId);
        when(bookProvider.findById(nonExistingBookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> bookServiceImpl.findById(String.valueOf(nonExistingBookId)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedBookNotFoundByIdMessage);

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verify(idFormatterComponent).formatId(String.valueOf(nonExistingBookId));
        verify(bookProvider).findById(nonExistingBookId);
    }

    @Test
    void when_nonExistingBookIsRequestedByIsbn_then_throwResourceNotFoundException() {

        // Arrange
        final String nonExistingBookIsbn = "9780306406157";
        final String expectedExistingBookByIsbnMessage = String.format(
                BOOK_NOT_FOUND_BY_ISBN_MESSAGE,
                nonExistingBookIsbn
        );

        when(mockErrorMessage()).thenReturn(expectedExistingBookByIsbnMessage);
        when(bookProvider.findByIsbn(nonExistingBookIsbn)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> bookServiceImpl.findByIsbn(nonExistingBookIsbn))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedExistingBookByIsbnMessage);

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verify(bookProvider).findByIsbn(nonExistingBookIsbn);
    }

    @Test
    void when_bookCreationIsRequested_and_existingBook_then_throwExistingResourceException() {

        // Arrange
        final var expectedBook = Instancio.create(Book.class);
        final var expectedExistingBookByIsbnMessage = String.format(
                EXISTING_BOOK_BY_ISBN_MESSAGE, expectedBook
                        .getIsbn()
        );

        when(authorService.findByName(expectedBook.getAuthor().getName())).thenReturn(expectedBook.getAuthor());
        when(bookProvider.existsByIsbn(expectedBook.getIsbn())).thenReturn(true);
        when(mockErrorMessage()).thenReturn(expectedExistingBookByIsbnMessage);

        // Act & Assert
        assertThatThrownBy(() -> bookServiceImpl.register(expectedBook))
                .isInstanceOf(ExistingResourceException.class)
                .hasMessage(expectedExistingBookByIsbnMessage);

        verify(authorService).findByName(expectedBook.getAuthor().getName());
        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verify(bookProvider).existsByIsbn(expectedBook.getIsbn());
        verify(bookProvider, times(0)).register(expectedBook);
    }

    @Test
    void when_bookCreationIsRequested_and_nonExistingBook_and_existingAuthorInUse_then_throwExistingResourceException1() {

        // Arrange
        final var expectedBook = Instancio.create(Book.class);
        final var otherBook = Instancio.create(Author.class).getBook();
        final var expectedAuthorInUseMessage = String.format(
                AUTHOR_IN_USE_MESSAGE,
                expectedBook.getAuthor().getName(),
                expectedBook.getIsbn()
        );

        expectedBook.getAuthor().setBook(otherBook);
        when(authorService.findByName(expectedBook.getAuthor().getName())).thenReturn(expectedBook.getAuthor());
        when(authorWithBookService.findByName(expectedBook.getAuthor().getName())).thenReturn(Optional.of(expectedBook.getAuthor()));
        when(mockErrorMessage()).thenReturn(expectedAuthorInUseMessage);

        // Act & Assert
        assertThatThrownBy(() -> bookServiceImpl.register(expectedBook))
                .isInstanceOf(ResourceInUseException.class)
                .hasMessage(expectedAuthorInUseMessage);

        verify(authorWithBookService).findByName(expectedBook.getAuthor().getName());
        verify(authorService).findByName(expectedBook.getAuthor().getName());
        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verify(bookProvider, times(0)).register(expectedBook);
    }

    @Test
    void when_bookDeletionByIdIsRequested_and_nonExistingBook_then_throwResourceNotFoundException() {

        // Arrange
        final long expectedBookId = 1;
        final String expectedBookNotFoundByIdMessage = String.format(BOOK_NOT_FOUND_BY_ID_MESSAGE, expectedBookId);

        when(mockErrorMessage()).thenReturn(expectedBookNotFoundByIdMessage);
        when(idFormatterComponent.formatId(String.valueOf(expectedBookId))).thenReturn(expectedBookId);
        when(bookProvider.existsById(expectedBookId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> bookServiceImpl.deleteById(String.valueOf(expectedBookId)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedBookNotFoundByIdMessage);

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verify(idFormatterComponent).formatId(String.valueOf(expectedBookId));
        verify(bookProvider).existsById(expectedBookId);
        verify(bookProvider, times(0)).deleteById(expectedBookId);
    }

    @Test
    void when_bookDeletionByIsbnIsRequested_and_nonExistingBook_then_throwResourceNotFoundException() {

        // Arrange
        final String nonExistingBookIsbn = "9780306406157";
        final String expectedBookNotFoundByIsbnMessage = String.format(
                BOOK_NOT_FOUND_BY_ISBN_MESSAGE,
                nonExistingBookIsbn
        );

        when(mockErrorMessage()).thenReturn(expectedBookNotFoundByIsbnMessage);
        when(bookProvider.existsByIsbn(nonExistingBookIsbn)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> bookServiceImpl.deleteByIsbn(nonExistingBookIsbn))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedBookNotFoundByIsbnMessage);

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verify(bookProvider).existsByIsbn(nonExistingBookIsbn);
        verify(bookProvider, times(0)).deleteByIsbn(nonExistingBookIsbn);
    }
}