package cleverton.heusner.adapter.input.mapper;

import cleverton.heusner.adapter.input.request.AuthorCreationRequest;
import cleverton.heusner.domain.model.Author;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorCreationRequestMapperTest {

    private AuthorCreationRequestMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(AuthorCreationRequestMapper.class);
    }

    @Test
    void when_authorCreationRequestMappedToAuthor_then_fieldsMappedWithSuccess() {
        // Arrange
        final var expectedBirthDate = LocalDate.now();
        final var authorCreationRequest = Instancio.of(AuthorCreationRequest.class)
                .set(Select.field(AuthorCreationRequest::birthDate), expectedBirthDate)
                .create();

        // Act
        final Author actualAuthor = mapper.toModel(authorCreationRequest);

        // Assert
        assertThat(actualAuthor.getName()).isEqualTo(authorCreationRequest.name());
        assertThat(actualAuthor.getBirthDate()).isEqualTo(authorCreationRequest.birthDate());

        assertThat(actualAuthor.getId()).isNull();
        assertThat(actualAuthor.getBook()).isNull();
        assertThat(actualAuthor.getCreatedDate()).isNull();
        assertThat(actualAuthor.getLastModifiedDate()).isNull();
    }
}