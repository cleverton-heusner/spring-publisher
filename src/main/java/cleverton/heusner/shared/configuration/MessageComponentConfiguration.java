package cleverton.heusner.shared.configuration;

import cleverton.heusner.port.shared.MessageComponent;
import cleverton.heusner.shared.utils.MessageComponentImpl;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageComponentConfiguration {

    @Bean
    public MessageComponent messageService(final MessageSource messageSource) {
        return new MessageComponentImpl(messageSource);
    }
}