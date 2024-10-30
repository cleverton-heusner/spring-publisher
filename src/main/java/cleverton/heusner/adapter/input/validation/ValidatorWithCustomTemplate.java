package cleverton.heusner.adapter.input.validation;

import cleverton.heusner.port.shared.MessageComponent;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class ValidatorWithCustomTemplate {

    @Autowired
    private MessageComponent messageComponent;
    protected ConstraintValidatorContext context;

    protected boolean createContext(final String messageKey, final Object... messageParams) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(messageComponent.getMessage(messageKey, messageParams))
                .addConstraintViolation();
        return false;
    }
}