package cleverton.heusner.validation.id;

import cleverton.heusner.validation.Validator;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class IdValidator extends Validator implements ConstraintValidator<Id, String> {

    @Override
    public boolean isValid(final String id, final ConstraintValidatorContext context) {
        this.context = context;

        if (StringUtils.isBlank(id)) {
            return createContext("NotNull.parameter.id");
        }

        long parsedId;
        try {
            parsedId = Long.parseLong(id);
        } catch (final NumberFormatException e) {
            return createContext("String.parameter.id");
        }

        if (parsedId == 0) {
            return createContext("Zero.parameter.id");
        }
        else if (parsedId < 0) {
            return createContext("Negative.parameter.id");
        }

        return true;
    }
}