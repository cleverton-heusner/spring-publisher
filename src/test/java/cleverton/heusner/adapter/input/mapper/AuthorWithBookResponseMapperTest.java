package cleverton.heusner.adapter.input.mapper;

import cleverton.heusner.domain.model.Author;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorWithBookResponseMapperTest {

    private AuthorWithBookResponseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(AuthorWithBookResponseMapper.class);
    }

    @Test
    void when_authorMappedToAuthorWithBookResponse_then_fieldsMappedWithSuccess() {
        // Arrange
        final var expectedAuthor = Instancio.create(Author.class);
        final var expectedBook = expectedAuthor.getBook();

        // Act
        final var actualAuthorWithBookResponse = mapper.toResponse(expectedAuthor);
        final var actualBookWithoutAuthorResponse = actualAuthorWithBookResponse.getBookWithoutAuthorResponse();

        // Assert
        assertThat(actualAuthorWithBookResponse.getId()).isEqualTo(expectedAuthor.getId());
        assertThat(actualAuthorWithBookResponse.getName()).isEqualTo(expectedAuthor.getName());
        assertThat(actualAuthorWithBookResponse.getBirthDate()).isEqualTo(expectedAuthor.getBirthDate());
        assertThat(actualAuthorWithBookResponse.getCreatedDate()).isEqualTo(expectedAuthor.getCreatedDate());
        assertThat(actualAuthorWithBookResponse.getLastModifiedDate()).isEqualTo(expectedAuthor.getLastModifiedDate());

        assertThat(actualBookWithoutAuthorResponse.getId()).isEqualTo(expectedBook.getId());
        assertThat(actualBookWithoutAuthorResponse.getTitle()).isEqualTo(expectedBook.getTitle());
        assertThat(actualBookWithoutAuthorResponse.getIsbn()).isEqualTo(expectedBook.getIsbn());
        assertThat(actualBookWithoutAuthorResponse.getCreatedDate()).isEqualTo(expectedBook.getCreatedDate());
        assertThat(actualBookWithoutAuthorResponse.getLastModifiedDate()).isEqualTo(expectedBook.getLastModifiedDate());
    }
}