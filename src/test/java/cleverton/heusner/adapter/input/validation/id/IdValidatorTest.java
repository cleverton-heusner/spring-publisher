package cleverton.heusner.adapter.input.validation.id;

import cleverton.heusner.adapter.input.validation.id.IdValidator;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IdValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @Mock
    private MessageComponent messageComponent;

    @InjectMocks
    private IdValidator idValidator;

    @Test
    void whenIdParameterIsPositiveNumber_thenIdParameterValidationReturnTrue() {

        // Arrange
        final String idAsPositiveNumber = "1";

        // Act
        boolean isAgeValid = idValidator.isValid(idAsPositiveNumber, context);

        // Assert
        assertThat(isAgeValid).isTrue();
    }

    @ParameterizedTest
    @MethodSource("getInvalidIds")
    void whenIdParameterIsInvalid_thenIdParameterValidationReturnFalse(final String invalidId) {

        // Arrange
        when(messageComponent.getMessage(anyString(), any(Object[].class))).thenReturn("Id inválido.");
        doNothing().when(context).disableDefaultConstraintViolation();
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addConstraintViolation()).thenReturn(context);

        // Act
        boolean isAgeValid = idValidator.isValid(invalidId, context);

        // Assert
        assertThat(isAgeValid).isFalse();

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(anyString());
        verify(constraintViolationBuilder).addConstraintViolation();
    }

    private static Stream<Arguments> getInvalidIds() {
        return Stream.of(
                Arguments.of("0"),
                Arguments.of("-1"),
                Arguments.of("a"),
                Arguments.of("A"),
                Arguments.of(""),
                Arguments.of("  "),
                Arguments.of(" 1 "),
                Arguments.of((Object) null)
        );
    }
}