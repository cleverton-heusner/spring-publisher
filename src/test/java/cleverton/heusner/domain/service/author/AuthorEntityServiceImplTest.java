package cleverton.heusner.domain.service.author;

import cleverton.heusner.adapter.output.entity.AuthorEntity;
import cleverton.heusner.domain.exception.ExistingResourceException;
import cleverton.heusner.domain.exception.ResourceNotFoundException;
import cleverton.heusner.domain.model.Author;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class AuthorEntityServiceImplTest extends AuthorServiceTestConfiguration {

    @Test
    void when_existingAuthorIsQueriedById_then_authorIsRetrieved() {

        // Arrange
        final var expectedAuthor = Instancio.create(Author.class);
        final long existingAuthorId = expectedAuthor.getId();

        when(idFormatterComponent.formatId(String.valueOf(existingAuthorId))).thenReturn(existingAuthorId);
        when(authorProvider.findById(existingAuthorId)).thenReturn(Optional.of(expectedAuthor));

        // Act
        final Author actualAuthor = authorServiceImpl.findById(String.valueOf(existingAuthorId));

        // Assert
        assertThat(actualAuthor).isEqualTo(expectedAuthor);

        verify(idFormatterComponent).formatId(String.valueOf(existingAuthorId));
        verify(authorProvider).findById(existingAuthorId);
    }

    @Test
    void when_existingAuthorIsFilteredByName_then_authorIsRetrieved() {

        // Arrange
        final String existingAuthorName = "Lovecraft";
        final Author expectedAuthor = Instancio.create(Author.class);

        when(authorProvider.findByName(existingAuthorName)).thenReturn(Optional.of(expectedAuthor));

        // Act
        final Author actualAuthor = authorServiceImpl.findByName(existingAuthorName);

        // Assert
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
        verify(authorProvider).findByName(existingAuthorName);
    }

    @Test
    void when_authorCreationIsRequestedAndNonExistingAuthor_then_authorIsCreated() {

        // Arrange
        final var expectedAuthor = getValidAuthor();

        when(authorProvider.findByName(expectedAuthor.getName())).thenReturn(Optional.empty());
        when(authorProvider.register(expectedAuthor)).thenReturn(expectedAuthor);

        // Act
        final Author actualAuthor = authorServiceImpl.register(expectedAuthor);

        // Assert
        assertThat(actualAuthor).isEqualTo(expectedAuthor);

        verify(authorProvider).findByName(expectedAuthor.getName());
        verify(authorProvider).register(expectedAuthor);
    }

    @Test
    void when_authorsListingIsRequestedAndExistingAuthors_then_authorsAreListed() {

        // Arrange
        final byte authorsSize = 2;
        final var expectedAuthors = Instancio.ofList(Author.class).size(authorsSize).create();

        when(authorProvider.findAll()).thenReturn(expectedAuthors);

        // Act
        final List<Author> actualAuthors = authorServiceImpl.findAll();

        // Assert
        assertThat(actualAuthors).isEqualTo(expectedAuthors);
        verify(authorProvider).findAll();
    }

    @Test
    void when_authorsListingIsRequestedAndNonExistingAuthors_then_emptyListIsReturned() {

        // Arrange
        final List<Author> expectedAuthors = Collections.emptyList();
        when(authorProvider.findAll()).thenReturn(expectedAuthors);

        // Act
        final List<Author> actualAuthors = authorServiceImpl.findAll();

        // Assert
        assertThat(actualAuthors).isEqualTo(expectedAuthors);
        verify(authorProvider).findAll();
    }

    @Test
    void when_authorDeletionByIdIsRequestedAndExistingAuthor_then_authorIsDeleted() {

        // Arrange
        final var expectedAuthorWithoutBook = Instancio.of(Author.class)
                .set(Select.field(Author::getBook), null)
                .create();
        final long authorId = expectedAuthorWithoutBook.getId();

        when(idFormatterComponent.formatId(String.valueOf(authorId))).thenReturn(authorId);
        when(authorProvider.findById(authorId)).thenReturn(Optional.of(expectedAuthorWithoutBook));
        doNothing().when(authorProvider).deleteById(authorId);

        // Act
        authorServiceImpl.deleteById(String.valueOf(authorId));

        // Assert
        verify(idFormatterComponent).formatId(String.valueOf(authorId));
        verify(authorProvider).findById(authorId);
        verify(authorProvider).deleteById(authorId);
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
        when(idFormatterComponent.formatId(String.valueOf(nonExistingAuthorId))).thenReturn(nonExistingAuthorId);
        when(authorProvider.findById(nonExistingAuthorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> authorServiceImpl.findById(String.valueOf(nonExistingAuthorId)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedBookNotFoundByIdMessage);

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verify(idFormatterComponent).formatId(String.valueOf(nonExistingAuthorId));
        verify(authorProvider).findById(nonExistingAuthorId);
    }

    @Test
    void when_authorCreationIsRequested_and_existingAuthor_then_throwExistingResourceException() {

        // Arrange
        final var expectedAuthor = getValidAuthor();
        final String expectedBookNotFoundByIdMessage = String.format(
                AUTHOR_NOT_FOUND_BY_ID_MESSAGE,
                expectedAuthor.getId()
        );

        when(mockErrorMessage()).thenReturn(expectedBookNotFoundByIdMessage);
        when(authorProvider.findByName(expectedAuthor.getName())).thenReturn(Optional.of(expectedAuthor));

        // Act & Assert
        assertThatThrownBy(() -> authorServiceImpl.register(expectedAuthor))
                .isInstanceOf(ExistingResourceException.class)
                .hasMessage(expectedBookNotFoundByIdMessage);

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verify(authorProvider).findByName(expectedAuthor.getName());
        verify(authorProvider, times(0)).register(expectedAuthor);
    }

    @Test
    void when_authorDeletionByIdIsRequestedAndNonExistingAuthor_then_throwResourceNotFoundException() {

        // Arrange
        final var author = Instancio.create(AuthorEntity.class);
        final long authorId = author.getId();
        final String expectedBookNotFoundByIdMessage = String.format(AUTHOR_NOT_FOUND_BY_ID_MESSAGE, authorId);

        when(mockErrorMessage()).thenReturn(expectedBookNotFoundByIdMessage);
        when(idFormatterComponent.formatId(String.valueOf(authorId))).thenReturn(authorId);
        when(authorProvider.findById(authorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> authorServiceImpl.deleteById(String.valueOf(authorId)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedBookNotFoundByIdMessage);

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verify(idFormatterComponent).formatId(String.valueOf(authorId));
        verify(authorProvider).findById(authorId);
        verify(authorProvider, times(0)).deleteById(authorId);
    }
}