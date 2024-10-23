package cleverton.heusner.validation.age;

import cleverton.heusner.validation.ValidatorWithCustomTemplate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

import static cleverton.heusner.constant.message.validation.AuthorMessageValidation.MINOR_OF_AGE_AUTHOR_BIRTH_DATE;
import static java.time.LocalDate.now;

@Component
public class AgeOfMajorityValidator extends ValidatorWithCustomTemplate implements ConstraintValidator<AgeOfMajority, LocalDate> {

    private int age;

    @Override
    public void initialize(final AgeOfMajority ageOfMajority) {
        this.age = ageOfMajority.age();
    }

    @Override
    public boolean isValid(final LocalDate authorBirthDate, final ConstraintValidatorContext context) {
        this.context = context;

        return Period.between(authorBirthDate, now()).getYears() >= age || createContext(
                MINOR_OF_AGE_AUTHOR_BIRTH_DATE,
                age
        );
    }
}