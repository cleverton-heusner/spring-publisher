package cleverton.heusner.service.author;

import cleverton.heusner.exception.resource.ExistingResourceException;
import cleverton.heusner.exception.resource.ResourceNotFoundException;
import cleverton.heusner.model.Author;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class AuthorServiceImplTest extends AuthorServiceTestConfiguration {

    @Test
    void when_existingAuthorIsQueriedById_then_authorIsRetrieved() {

        // Arrange
        final var expectedAuthor = Instancio.create(Author.class);
        final long existingAuthorId = expectedAuthor.getId();

        when(idFormatterService.formatId(String.valueOf(existingAuthorId))).thenReturn(existingAuthorId);
        when(authorRepository.findById(existingAuthorId)).thenReturn(Optional.of(expectedAuthor));

        // Act
        final Author actualAuthor = authorServiceImpl.findById(String.valueOf(existingAuthorId));

        // Assert
        assertThat(actualAuthor).isEqualTo(expectedAuthor);

        verify(idFormatterService).formatId(String.valueOf(existingAuthorId));
        verify(authorRepository).findById(existingAuthorId);
    }

    @Test
    void when_existingAuthorIsFilteredByName_then_authorIsRetrieved() {

        // Arrange
        final String existingAuthorName = "Lovecraft";
        final Author expectedAuthor = Instancio.create(Author.class);

        when(authorRepository.findByNameIgnoreCase(existingAuthorName)).thenReturn(Optional.of(expectedAuthor));

        // Act
        final Author actualAuthor = authorServiceImpl.findByName(existingAuthorName);

        // Assert
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
        verify(authorRepository).findByNameIgnoreCase(existingAuthorName);
    }

    @Test
    void when_authorCreationIsRequestedAndNonExistingAuthor_then_authorIsCreated() {

        // Arrange
        final var expectedAuthor = Instancio.create(Author.class);

        when(authorRepository.findByNameIgnoreCase(expectedAuthor.getName())).thenReturn(Optional.empty());
        when(authorRepository.save(expectedAuthor)).thenReturn(expectedAuthor);

        // Act
        final Author actualAuthor = authorServiceImpl.register(expectedAuthor);

        // Assert
        assertThat(actualAuthor).isEqualTo(expectedAuthor);

        verify(authorRepository).findByNameIgnoreCase(expectedAuthor.getName());
        verify(authorRepository).save(expectedAuthor);
    }

    @Test
    void when_authorsListingIsRequestedAndExistingAuthors_then_authorsAreListed() {

        // Arrange
        final byte authorsSize = 2;
        final var expectedAuthors = Instancio.ofList(Author.class).size(authorsSize).create();

        when(authorRepository.findAll()).thenReturn(expectedAuthors);

        // Act
        final List<Author> actualAuthors = authorServiceImpl.findAll();

        // Assert
        assertThat(actualAuthors).isEqualTo(expectedAuthors);
        verify(authorRepository).findAll();
    }

    @Test
    void when_authorsListingIsRequestedAndNonExistingAuthors_then_emptyListIsReturned() {

        // Arrange
        final List<Author> expectedAuthors = Collections.emptyList();
        when(authorRepository.findAll()).thenReturn(expectedAuthors);

        // Act
        final List<Author> actualAuthors = authorServiceImpl.findAll();

        // Assert
        assertThat(actualAuthors).isEqualTo(expectedAuthors);
        verify(authorRepository).findAll();
    }

    @Test
    void when_authorDeletionByIdIsRequestedAndExistingAuthor_then_authorIsDeleted() {

        // Arrange
        final var expectedAuthorWithoutBook = Instancio.of(Author.class)
                .set(Select.field(Author::getBook), null)
                .create();
        final long authorId = expectedAuthorWithoutBook.getId();

        when(idFormatterService.formatId(String.valueOf(authorId))).thenReturn(authorId);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(expectedAuthorWithoutBook));
        doNothing().when(authorRepository).deleteById(authorId);

        // Act
        authorServiceImpl.deleteById(String.valueOf(authorId));

        // Assert
        verify(idFormatterService).formatId(String.valueOf(authorId));
        verify(authorRepository).findById(authorId);
        verify(authorRepository).deleteById(authorId);
    }

    @Test
    void when_nonExistingAuthorIsRequestedById_then_throwResourceNotFoundException() {

        // Arrange
        final long nonExistingAuthorId = 1;
        final String expectedBookNotFoundByIdMessage = String.format(
                AUTHOR_NOT_FOUND_BY_ID_MESSAGE,
                nonExistingAuthorId
        );

        when(mockErrorMessage()).thenReturn(expectedBookNotFoundByIdMessage);
        when(idFormatterService.formatId(String.valueOf(nonExistingAuthorId))).thenReturn(nonExistingAuthorId);
        when(authorRepository.findById(nonExistingAuthorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> authorServiceImpl.findById(String.valueOf(nonExistingAuthorId)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedBookNotFoundByIdMessage);

        verify(messageService).getMessage(anyString(), any(Object[].class));
        verify(idFormatterService).formatId(String.valueOf(nonExistingAuthorId));
        verify(authorRepository).findById(nonExistingAuthorId);
    }

    @Test
    void when_authorCreationIsRequested_and_existingAuthor_then_throwExistingResourceException() {

        // Arrange
        final var expectedAuthor = Instancio.create(Author.class);
        final String expectedBookNotFoundByIdMessage = String.format(
                AUTHOR_NOT_FOUND_BY_ID_MESSAGE,
                expectedAuthor.getId()
        );

        when(mockErrorMessage()).thenReturn(expectedBookNotFoundByIdMessage);
        when(authorRepository.findByNameIgnoreCase(expectedAuthor.getName())).thenReturn(Optional.of(expectedAuthor));

        // Act & Assert
        assertThatThrownBy(() -> authorServiceImpl.register(expectedAuthor))
                .isInstanceOf(ExistingResourceException.class)
                .hasMessage(expectedBookNotFoundByIdMessage);

        verify(messageService).getMessage(anyString(), any(Object[].class));
        verify(authorRepository).findByNameIgnoreCase(expectedAuthor.getName());
        verify(authorRepository, times(0)).save(expectedAuthor);
    }

    @Test
    void when_authorDeletionByIdIsRequestedAndNonExistingAuthor_then_throwResourceNotFoundException() {

        // Arrange
        final var author = Instancio.create(Author.class);
        final long authorId = author.getId();
        final String expectedBookNotFoundByIdMessage = String.format(AUTHOR_NOT_FOUND_BY_ID_MESSAGE, authorId);

        when(mockErrorMessage()).thenReturn(expectedBookNotFoundByIdMessage);
        when(idFormatterService.formatId(String.valueOf(authorId))).thenReturn(authorId);
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> authorServiceImpl.deleteById(String.valueOf(authorId)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedBookNotFoundByIdMessage);

        verify(messageService).getMessage(anyString(), any(Object[].class));
        verify(idFormatterService).formatId(String.valueOf(authorId));
        verify(authorRepository).findById(authorId);
        verify(authorRepository, times(0)).deleteById(authorId);
    }
}