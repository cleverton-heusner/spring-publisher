package cleverton.heusner.domain.configuration;

import cleverton.heusner.domain.service.author.AuthorServiceImpl;
import cleverton.heusner.port.input.utils.IdFormatterComponent;
import cleverton.heusner.port.output.provider.author.AuthorProvider;
import cleverton.heusner.port.shared.MessageComponent;
import cleverton.heusner.port.input.service.author.AuthorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorServiceConfiguration {

    @Bean
    public AuthorService authorService(final AuthorProvider authorProvider,
                                       final MessageComponent messageComponent,
                                       final IdFormatterComponent idFormatterComponent) {
        return new AuthorServiceImpl(authorProvider, messageComponent, idFormatterComponent);
    }
}