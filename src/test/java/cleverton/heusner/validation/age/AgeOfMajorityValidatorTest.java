package cleverton.heusner.validation.age;

import cleverton.heusner.service.message.MessageService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgeOfMajorityValidatorTest {

    private static final int AGE_OF_MAJORITY = 18;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private AgeOfMajorityValidator validator;

    @Test
    void whenAuthorAgeIsMajority_thenAgeIsValid() {

        // Arrange
        final var ageOfMajority = mock(AgeOfMajority.class);
        final var birthDate = LocalDate.now().minusYears(AGE_OF_MAJORITY);

        when(ageOfMajority.age()).thenReturn(AGE_OF_MAJORITY);
        validator.initialize(ageOfMajority);

        // Act
        boolean isAgeValid = validator.isValid(birthDate, context);

        // Assert
        assertThat(isAgeValid).isTrue();
        verify(ageOfMajority).age();
    }

    @Test
    void whenAuthorAgeIsOverMajority_thenAgeIsValid() {

        // Arrange
        final int overMajorityAge = 19;
        final var ageOfMajority = mock(AgeOfMajority.class);
        final var birthDate = LocalDate.now().minusYears(overMajorityAge);

        when(ageOfMajority.age()).thenReturn(AGE_OF_MAJORITY);
        validator.initialize(ageOfMajority);

        // Act
        boolean isAgeValid = validator.isValid(birthDate, context);

        // Assert
        assertThat(isAgeValid).isTrue();
        verify(ageOfMajority).age();
    }

    @Test
    void whenAuthorAgeIsMinority_thenAgeIsInvalid() {

        // Arrange
        final int minorityAge = 16;
        final var ageOfMajority = mock(AgeOfMajority.class);
        final var birthDate = LocalDate.now().minusYears(minorityAge);

        when(ageOfMajority.age()).thenReturn(AGE_OF_MAJORITY);
        when(messageService.getMessage(anyString(), any(Object[].class))).thenReturn(
                "Authors under " + AGE_OF_MAJORITY + " years of old cannot publish books."
        );
        doNothing().when(context).disableDefaultConstraintViolation();
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addConstraintViolation()).thenReturn(context);

        validator.initialize(ageOfMajority);

        // Act
        boolean isAgeValid = validator.isValid(birthDate, context);

        // Assert
        assertThat(isAgeValid).isFalse();

        verify(ageOfMajority).age();
        verify(messageService).getMessage(anyString(), any(Object[].class));
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(anyString());
        verify(constraintViolationBuilder).addConstraintViolation();
    }
}