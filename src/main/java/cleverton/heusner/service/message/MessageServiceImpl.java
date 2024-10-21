package cleverton.heusner.service.message;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;

    public MessageServiceImpl(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(final String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}