package cleverton.heusner.validation.id;

import cleverton.heusner.validation.ValidatorWithCustomTemplate;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import static cleverton.heusner.constant.message.validation.CommonMessageValidation.*;

@Component
public class IdValidator extends ValidatorWithCustomTemplate implements ConstraintValidator<Id, String> {

    @Override
    public boolean isValid(final String id, final ConstraintValidatorContext context) {
        this.context = context;

        if (StringUtils.isBlank(id)) {
            return createContext(NOT_NULL_PARAMETER_ID);
        }

        long parsedId;
        try {
            parsedId = Long.parseLong(id);
        } catch (final NumberFormatException e) {
            return createContext(STRING_PARAMETER_ID);
        }

        if (parsedId == 0) {
            return createContext(ZERO_PARAMETER_ID);
        }
        else if (parsedId < 0) {
            return createContext(NEGATIVE_PARAMETER_ID);
        }

        return true;
    }
}