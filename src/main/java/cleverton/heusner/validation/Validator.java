package cleverton.heusner.validation;

import cleverton.heusner.service.message.MessageService;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class Validator {

    @Autowired
    private MessageService messageService;
    protected ConstraintValidatorContext context;

    protected boolean createContext(final String messageKey, final Object... messageParams) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(messageService.getMessage(messageKey, messageParams))
                .addConstraintViolation();
        return false;
    }
}