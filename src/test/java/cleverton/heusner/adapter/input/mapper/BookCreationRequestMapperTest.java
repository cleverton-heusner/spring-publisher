package cleverton.heusner.adapter.input.mapper;

import cleverton.heusner.adapter.input.request.BookCreationRequest;
import cleverton.heusner.domain.model.Author;
import cleverton.heusner.domain.model.Book;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class BookCreationRequestMapperTest {

    private BookCreationRequestMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(BookCreationRequestMapper.class);
    }

    @Test
    void when_bookCreationRequestMappedToBook_then_fieldsMappedWithSuccess() {

        // Arrange
        final var bookCreationRequest = Instancio.create(BookCreationRequest.class);

        // Act
        final Book actualBook = mapper.toModel(bookCreationRequest);
        final Author actualAuthor = actualBook.getAuthor();

        // Assert
        assertThat(actualBook.getId()).isNull();
        assertThat(actualBook.getTitle()).isEqualTo(bookCreationRequest.title());
        assertThat(actualBook.getIsbn()).isEqualTo(bookCreationRequest.isbn());
        assertThat(actualBook.getCreatedDate()).isNull();
        assertThat(actualBook.getLastModifiedDate()).isNull();

        assertThat(actualAuthor.getId()).isNull();
        assertThat(actualAuthor.getName()).isEqualTo(bookCreationRequest.authorName());
        assertThat(actualAuthor.getBirthDate()).isNull();
        assertThat(actualAuthor.getCreatedDate()).isNull();
        assertThat(actualAuthor.getLastModifiedDate()).isNull();
        assertThat(actualAuthor.getBook()).isNull();
    }
}