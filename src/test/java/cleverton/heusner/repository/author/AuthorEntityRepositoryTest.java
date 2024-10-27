package cleverton.heusner.repository.author;

import cleverton.heusner.adapter.output.entity.AuthorEntity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorEntityRepositoryTest extends AuthorRepositoryTestConfiguration {

    @Test
    void when_existingAuthorIsQueriedById_then_authorIsRetrieved() {

        // Arrange
        final var expectedAuthor = authorRepository.save(createAuthorWithoutBook());

        // Act
        final AuthorEntity actualAuthorEntity = findAuthorById(expectedAuthor.getId());

        // Assert
        assertThat(actualAuthorEntity).isEqualTo(expectedAuthor);
    }

    @Test
    void when_existingAuthorIsFilteredByName_then_authorIsRetrieved() {

        // Arrange
        final var expectedAuthor = authorRepository.save(createAuthorWithoutBook());

        // Act
        final AuthorEntity actualAuthorEntity = authorRepository.findByNameIgnoreCase(expectedAuthor.getName()).orElse(null);

        // Assert
        assertThat(actualAuthorEntity).isEqualTo(expectedAuthor);
    }

    @Test
    void when_existingAuthorsWithDifferentNamesAreFilteredByName_then_onlyOneAuthorIsRetrieved() {

        // Arrange
        final byte allAuthorsSize = 2;
        final var expectedAuthor = IntStream.range(0, allAuthorsSize)
                .mapToObj(_ -> authorRepository.save(createAuthorWithoutBook()))
                .findFirst()
                .orElse(null);

        // Act
        assert expectedAuthor != null;
        final var actualAuthor = authorRepository.findByNameIgnoreCase(expectedAuthor.getName())
                .orElse(null);

        // Assert
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    void when_authorCreationIsRequestedAndNonExistingAuthor_then_authorIsCreated() {

        // Arrange
        final var expectedAuthor = createAuthorWithoutBook();

        // Act
        final var actualAuthor = authorRepository.save(expectedAuthor);

        // Assert
        assertThat(actualAuthor.getName()).isEqualTo(expectedAuthor.getName());
        assertThat(actualAuthor.getBook()).isEqualTo(expectedAuthor.getBook());
        assertThat(actualAuthor.getId()).isNotNull();
    }

    @Test
    void when_authorsListingIsRequestedAndExistingAuthors_then_authorsAreListed() {

        // Arrange
        final byte authorsSize = 2;
        final List<AuthorEntity> expectedAuthorEntities = IntStream.range(0, authorsSize)
                .mapToObj(_ -> createAuthorWithoutBook())
                .toList();

        // Act
        final List<AuthorEntity> actualAuthorEntities = authorRepository.findAll();

        // Assert
        assertThat(actualAuthorEntities).isEqualTo(expectedAuthorEntities);
    }

    @Test
    void when_authorsListingIsRequestedAndNonExistingAuthors_then_emptyListIsReturned() {

        // Act
        final List<AuthorEntity> actualAuthorEntities = authorRepository.findAll();

        // Assert
        assertThat(actualAuthorEntities).isEmpty();
    }

    @Test
    void when_authorDeletionByIdIsRequestedAndExistingAuthor_then_authorIsDeleted() {

        // Arrange
        final var expectedAuthor = authorRepository.save(createAuthorWithoutBook());

        // Act
        authorRepository.deleteById(expectedAuthor.getId());

        // Assert
        final var actualAuthor = findAuthorById(expectedAuthor.getId());
        assertThat(actualAuthor).isNull();
    }

    @Test
    void when_nonExistingAuthorIsQueriedById_then_nullIsReturned() {

        // Arrange
        final long nonExistingAuthorId = 1;

        // Act
        final var actualAuthor = authorRepository.findById(nonExistingAuthorId).orElse(null);

        // Assert
        assertThat(actualAuthor).isNull();
    }
}