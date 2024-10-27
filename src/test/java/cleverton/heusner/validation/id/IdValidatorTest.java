package cleverton.heusner.validation.id;

import cleverton.heusner.adapter.input.validation.id.IdValidator;
import cleverton.heusner.port.shared.MessageComponent;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IdValidatorTest {

    private static final int AGE_OF_MAJORITY = 18;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @Mock
    private MessageComponent messageComponent;

    @InjectMocks
    private IdValidator idValidator;

    @Test
    void whenIdParameterIsPositive_thenIdParameterIsValid() {

        // Arrange
        final String positiveId = "1";

        // Act
        boolean isAgeValid = idValidator.isValid(positiveId, context);

        // Assert
        assertThat(isAgeValid).isTrue();
    }

    @Test
    void whenIdParameterIsZero_thenIdParameterIsInvalid() {

        // Arrange
        final String zeroId = "0";

        when(messageComponent.getMessage(anyString(), any(Object[].class))).thenReturn(
                "Id deve ser maior que zero."
        );
        doNothing().when(context).disableDefaultConstraintViolation();
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addConstraintViolation()).thenReturn(context);

        // Act
        boolean isAgeValid = idValidator.isValid(zeroId, context);

        // Assert
        assertThat(isAgeValid).isFalse();

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(anyString());
        verify(constraintViolationBuilder).addConstraintViolation();
    }

    @Test
    void whenIdParameterIsNegative_thenIdParameterIsInvalid() {

        // Arrange
        final String negativeId = "-1";

        when(messageComponent.getMessage(anyString(), any(Object[].class))).thenReturn(
                "Id deve ser positivo."
        );
        doNothing().when(context).disableDefaultConstraintViolation();
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addConstraintViolation()).thenReturn(context);

        // Act
        boolean isAgeValid = idValidator.isValid(negativeId, context);

        // Assert
        assertThat(isAgeValid).isFalse();

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(anyString());
        verify(constraintViolationBuilder).addConstraintViolation();
    }

    @Test
    void whenIdParameterIsBlank_thenIdParameterIsInvalid() {

        // Arrange
        final String negativeId = "";

        when(messageComponent.getMessage(anyString(), any(Object[].class))).thenReturn(
                "Id é obrigatório."
        );
        doNothing().when(context).disableDefaultConstraintViolation();
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addConstraintViolation()).thenReturn(context);

        // Act
        boolean isAgeValid = idValidator.isValid(negativeId, context);

        // Assert
        assertThat(isAgeValid).isFalse();

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(anyString());
        verify(constraintViolationBuilder).addConstraintViolation();
    }

    @Test
    void whenIdParameterIsNull_thenIdParameterIsInvalid() {

        // Arrange
        when(messageComponent.getMessage(anyString(), any(Object[].class))).thenReturn(
                "Id é obrigatório."
        );
        doNothing().when(context).disableDefaultConstraintViolation();
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addConstraintViolation()).thenReturn(context);

        // Act
        boolean isAgeValid = idValidator.isValid(null, context);

        // Assert
        assertThat(isAgeValid).isFalse();

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(anyString());
        verify(constraintViolationBuilder).addConstraintViolation();
    }
}