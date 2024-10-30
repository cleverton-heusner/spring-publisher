package cleverton.heusner.adapter.input.mapper;

import cleverton.heusner.domain.model.Book;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class BookWithAuthorResponseMapperTest {

    private BookWithAuthorResponseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(BookWithAuthorResponseMapper.class);
    }

    @Test
    void when_bookMappedToBookWithAuthorResponse_then_fieldsMappedWithSuccess() {

        // Arrange
        final var expectedBook = Instancio.create(Book.class);
        final var expectedAuthor = expectedBook.getAuthor();

        // Act
        final var actualBookWithAuthorResponse = mapper.toResponse(expectedBook);
        final var authorWithoutBookResponse = actualBookWithAuthorResponse.getAuthorWithoutBookResponse();

        // Assert
        assertThat(actualBookWithAuthorResponse.getId()).isEqualTo(expectedBook.getId());
        assertThat(actualBookWithAuthorResponse.getIsbn()).isEqualTo(expectedBook.getIsbn());
        assertThat(actualBookWithAuthorResponse.getTitle()).isEqualTo(expectedBook.getTitle());
        assertThat(actualBookWithAuthorResponse.getCreatedDate()).isEqualTo(expectedBook.getCreatedDate());
        assertThat(actualBookWithAuthorResponse.getLastModifiedDate()).isEqualTo(expectedBook.getLastModifiedDate());

        assertThat(authorWithoutBookResponse.getId()).isEqualTo(expectedAuthor.getId());
        assertThat(authorWithoutBookResponse.getName()).isEqualTo(expectedAuthor.getName());
        assertThat(authorWithoutBookResponse.getBirthDate()).isEqualTo(expectedAuthor.getBirthDate());
        assertThat(authorWithoutBookResponse.getCreatedDate()).isEqualTo(expectedAuthor.getCreatedDate());
        assertThat(authorWithoutBookResponse.getLastModifiedDate()).isEqualTo(expectedAuthor.getLastModifiedDate());
    }
}