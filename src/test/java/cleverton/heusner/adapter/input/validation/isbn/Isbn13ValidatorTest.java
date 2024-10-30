package cleverton.heusner.adapter.input.validation.isbn;

import cleverton.heusner.port.shared.MessageComponent;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Isbn13ValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @Mock
    private MessageComponent messageComponent;

    @InjectMocks
    private Isbn13Validator isbn13Validator;

    @Test
    void whenIdIsbn13IsValid_thenIsbn13ValidationReturnTrue() {

        // Arrange
        final String validIsbn13 = "9788248141334";

        // Act
        boolean isIsbnValid = isbn13Validator.isValid(validIsbn13, context);

        // Assert
        assertThat(isIsbnValid).isTrue();
    }

    @ParameterizedTest
    @MethodSource("getInvalidIsbns")
    void whenIsbnIsInvalid_thenIsbnValidationReturnFalse(final String invalidIsbn) {

        // Arrange
        when(messageComponent.getMessage(anyString(), any(Object[].class))).thenReturn("ISBN inválido.");
        doNothing().when(context).disableDefaultConstraintViolation();
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addConstraintViolation()).thenReturn(context);

        // Act
        boolean isIsbnValid = isbn13Validator.isValid(invalidIsbn, context);

        // Assert
        assertThat(isIsbnValid).isFalse();

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(anyString());
        verify(constraintViolationBuilder).addConstraintViolation();
    }

    private static Stream<Arguments> getInvalidIsbns() {
        return Stream.of(
                Arguments.of("9788248141331"),
                Arguments.of(" 9788248141331 "),
                Arguments.of(""),
                Arguments.of((Object) null)
        );
    }
}