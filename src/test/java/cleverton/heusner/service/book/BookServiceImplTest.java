package cleverton.heusner.service.book;

import cleverton.heusner.exception.resource.ExistingResourceException;
import cleverton.heusner.exception.resource.ResourceInUseException;
import cleverton.heusner.exception.resource.ResourceNotFoundException;
import cleverton.heusner.model.Author;
import cleverton.heusner.model.Book;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class BookServiceImplTest extends BookServiceTestConfiguration {

    @Test
    void when_existingBookIsRequestedById_then_bookIsRetrieved() {

        // Arrange
        final long existingBookId = 1;
        final var expectedBook = Instancio.create(Book.class);

        when(idFormatterService.formatId(String.valueOf(existingBookId))).thenReturn(existingBookId);
        when(bookRepository.findById(existingBookId)).thenReturn(Optional.of(expectedBook));

        // Act
        final Book actualBook = bookServiceImpl.findById(String.valueOf(existingBookId));

        // Assert
        assertThat(actualBook).isEqualTo(expectedBook);

        verify(idFormatterService).formatId(String.valueOf(existingBookId));
        verify(bookRepository).findById(existingBookId);
    }

    @Test
    void when_existingBookIsRequestedByIsbn_then_bookIsRetrieved() {

        // Arrange
        final String existingBookIsbn = "9780306406157";
        final var expectedBook = Instancio.create(Book.class);

        when(bookRepository.findByIsbn(existingBookIsbn)).thenReturn(Optional.of(expectedBook));

        // Act
        final Book actualBook = bookServiceImpl.findByIsbn(existingBookIsbn);

        // Assert
        assertThat(actualBook).isEqualTo(expectedBook);
        verify(bookRepository).findByIsbn(existingBookIsbn);
    }

    @Test
    void when_bookCreationIsRequested_and_nonExistingBook_and_existingAuthorWithoutBook_then_bookIsCreated() {

        // Arrange
        final var expectedBook = Instancio.create(Book.class);
        final var expectedAuthor = expectedBook.getAuthor();

        when(authorService.findByName(expectedAuthor.getName())).thenReturn(expectedAuthor);
        when(authorWithBookService.findFirstByNameAndWithBook(expectedAuthor.getName())).thenReturn(Optional.empty());
        when(existingBookRepository.existsByIsbn(expectedBook.getIsbn())).thenReturn(false);
        when(bookRepository.save(expectedBook)).thenReturn(expectedBook);

        // Act
        final Book actualBook = bookServiceImpl.register(expectedBook);

        // Assert
        assertThat(actualBook).isEqualTo(expectedBook);

        verify(authorService).findByName(expectedAuthor.getName());
        verify(authorWithBookService).findFirstByNameAndWithBook(expectedAuthor.getName());
        verify(existingBookRepository).existsByIsbn(expectedBook.getIsbn());
        verify(bookRepository).save(expectedBook);
    }

    @Test
    void when_booksListingIsRequested_and_existingBooks_then_booksAreListed() {

        // Arrange
        final byte booksSize = 2;
        final var expectedBooks = Instancio.ofList(Book.class).size(booksSize).create();

        when(bookRepository.findAll()).thenReturn(expectedBooks);

        // Act
        final List<Book> actualBooks = bookServiceImpl.findAll();

        // Assert
        assertThat(actualBooks).isEqualTo(expectedBooks);
        verify(bookRepository).findAll();
    }

    @Test
    void when_booksListingIsRequested_and_nonExistingBooks_then_emptyListIsReturned() {

        // Arrange
        final List<Book> expectedBooks = Collections.emptyList();

        when(bookRepository.findAll()).thenReturn(expectedBooks);

        // Act
        final List<Book> actualBooks = bookServiceImpl.findAll();

        // Assert
        assertThat(actualBooks).isEqualTo(expectedBooks);
        verify(bookRepository).findAll();
    }

    @Test
    void when_bookDeletionByIdIsRequested_and_existingBook_then_bookIsDeleted() {

        // Arrange
        final long bookId = 1;

        when(idFormatterService.formatId(String.valueOf(bookId))).thenReturn(bookId);
        when(bookRepository.existsById(bookId)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(bookId);

        // Act
        bookServiceImpl.deleteById(String.valueOf(bookId));

        // Assert
        verify(idFormatterService).formatId(String.valueOf(bookId));
        verify(bookRepository).existsById(bookId);
        verify(bookRepository).deleteById(bookId);
    }

    @Test
    void when_bookDeletionByIdIsbnRequested_and_existingBook_then_bookIsDeleted() {

        // Arrange
        final String isbn = "9780306406157";

        when(existingBookRepository.existsByIsbn(isbn)).thenReturn(true);
        doNothing().when(bookRepository).deleteByIsbn(isbn);

        // Act
        bookServiceImpl.deleteByIsbn(isbn);

        // Assert
        verify(existingBookRepository).existsByIsbn(isbn);
        verify(bookRepository).deleteByIsbn(isbn);
    }

    @Test
    void when_nonExistingBookIsRequestedById_then_throwResourceNotFoundException() {

        // Arrange
        final long nonExistingBookId = 1;
        final String expectedBookNotFoundByIdMessage = String.format(BOOK_NOT_FOUND_BY_ID_MESSAGE, nonExistingBookId);

        when(mockErrorMessage()).thenReturn(expectedBookNotFoundByIdMessage);
        when(idFormatterService.formatId(String.valueOf(nonExistingBookId))).thenReturn(nonExistingBookId);
        when(bookRepository.findById(nonExistingBookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> bookServiceImpl.findById(String.valueOf(nonExistingBookId)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedBookNotFoundByIdMessage);

        verify(messageService).getMessage(anyString(), any(Object[].class));
        verify(idFormatterService).formatId(String.valueOf(nonExistingBookId));
        verify(bookRepository).findById(nonExistingBookId);
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
        when(bookRepository.findByIsbn(nonExistingBookIsbn)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> bookServiceImpl.findByIsbn(nonExistingBookIsbn))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedExistingBookByIsbnMessage);

        verify(messageService).getMessage(anyString(), any(Object[].class));
        verify(bookRepository).findByIsbn(nonExistingBookIsbn);
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
        when(existingBookRepository.existsByIsbn(expectedBook.getIsbn())).thenReturn(true);
        when(mockErrorMessage()).thenReturn(expectedExistingBookByIsbnMessage);

        // Act & Assert
        assertThatThrownBy(() -> bookServiceImpl.register(expectedBook))
                .isInstanceOf(ExistingResourceException.class)
                .hasMessage(expectedExistingBookByIsbnMessage);

        verify(authorService).findByName(expectedBook.getAuthor().getName());
        verify(messageService).getMessage(anyString(), any(Object[].class));
        verify(existingBookRepository).existsByIsbn(expectedBook.getIsbn());
        verify(bookRepository, times(0)).save(expectedBook);
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
        when(authorWithBookService.findFirstByNameAndWithBook(expectedBook.getAuthor().getName())).thenReturn(Optional.of(expectedBook.getAuthor()));
        when(mockErrorMessage()).thenReturn(expectedAuthorInUseMessage);

        // Act & Assert
        assertThatThrownBy(() -> bookServiceImpl.register(expectedBook))
                .isInstanceOf(ResourceInUseException.class)
                .hasMessage(expectedAuthorInUseMessage);

        verify(authorWithBookService).findFirstByNameAndWithBook(expectedBook.getAuthor().getName());
        verify(authorService).findByName(expectedBook.getAuthor().getName());
        verify(messageService).getMessage(anyString(), any(Object[].class));
        verify(bookRepository, times(0)).save(expectedBook);
    }

    @Test
    void when_bookDeletionByIdIsRequested_and_nonExistingBook_then_throwResourceNotFoundException() {

        // Arrange
        final long expectedBookId = 1;
        final String expectedBookNotFoundByIdMessage = String.format(BOOK_NOT_FOUND_BY_ID_MESSAGE, expectedBookId);

        when(mockErrorMessage()).thenReturn(expectedBookNotFoundByIdMessage);
        when(idFormatterService.formatId(String.valueOf(expectedBookId))).thenReturn(expectedBookId);
        when(bookRepository.existsById(expectedBookId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> bookServiceImpl.deleteById(String.valueOf(expectedBookId)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedBookNotFoundByIdMessage);

        verify(messageService).getMessage(anyString(), any(Object[].class));
        verify(idFormatterService).formatId(String.valueOf(expectedBookId));
        verify(bookRepository).existsById(expectedBookId);
        verify(bookRepository, times(0)).deleteById(expectedBookId);
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
        when(existingBookRepository.existsByIsbn(nonExistingBookIsbn)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> bookServiceImpl.deleteByIsbn(nonExistingBookIsbn))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedBookNotFoundByIsbnMessage);

        verify(messageService).getMessage(anyString(), any(Object[].class));
        verify(existingBookRepository).existsByIsbn(nonExistingBookIsbn);
        verify(bookRepository, times(0)).deleteByIsbn(nonExistingBookIsbn);
    }
}