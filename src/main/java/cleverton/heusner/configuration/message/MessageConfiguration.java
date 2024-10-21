package cleverton.heusner.configuration.message;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import static cleverton.heusner.configuration.message.MessageBasename.*;

@Configuration
public class MessageConfiguration {

    public static final String ENCODING = "ISO-8859-1";
    public static final String FILE_FORMAT = ".properties";

    @Bean
    public MessageSource messageSource() {
        final var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(MESSAGES, VALIDATION_MESSAGES, SCHEMA_MESSAGES, API_MESSAGES);
        messageSource.setDefaultEncoding(ENCODING);
        messageSource.setDefaultLocale(LocaleContextHolder.getLocale());

        return messageSource;
    }
}
