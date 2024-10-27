package cleverton.heusner.shared.utils;

import cleverton.heusner.port.shared.MessageComponent;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class MessageComponentImpl implements MessageComponent {

    private final MessageSource messageSource;

    public MessageComponentImpl(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(final String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}