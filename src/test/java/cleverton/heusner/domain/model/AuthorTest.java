package cleverton.heusner.domain.model;

import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorTest {

    private static final int AGE_OF_MAJORITY = 18;

    @Test
    void when_AuthorIsOfLegalAge_then_returnAgeIsValid() {

        // Arrange
        final var birthDate = LocalDate.now().minusYears(AGE_OF_MAJORITY);
        final var author = Instancio.of(Author.class)
                .set(Select.field("birthDate"), birthDate)
                .create();

        // Act
        final boolean isAuthorOfLegalAge = author.isOfLegalAge(AGE_OF_MAJORITY);

        // Assert
        assertThat(isAuthorOfLegalAge).isTrue();
    }

    @Test
    void when_AuthorIsOverOfLegalAge_then_returnAgeIsValid() {

        // Arrange
        final var birthDate = LocalDate.now().minusYears(19);
        final var author = Instancio.of(Author.class)
                .set(Select.field("birthDate"), birthDate)
                .create();

        // Act
        final boolean isAuthorOfLegalAge = author.isOfLegalAge(AGE_OF_MAJORITY);

        // Assert
        assertThat(isAuthorOfLegalAge).isTrue();
    }

    @Test
    void when_AuthorIsUnderOfLegalAge_then_returnAgeIsInvalid() {

        // Arrange
        final var birthDate = LocalDate.now().minusYears(17);
        final var author = Instancio.of(Author.class)
                .set(Select.field("birthDate"), birthDate)
                .create();

        // Act
        final boolean isAuthorOfLegalAge = author.isOfLegalAge(AGE_OF_MAJORITY);

        // Assert
        assertThat(isAuthorOfLegalAge).isFalse();
    }
}
