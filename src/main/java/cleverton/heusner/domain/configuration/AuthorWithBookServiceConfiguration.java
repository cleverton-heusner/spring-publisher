package cleverton.heusner.domain.configuration;

import cleverton.heusner.domain.service.author.AuthorWithBookServiceImpl;
import cleverton.heusner.port.input.service.author.AuthorWithBookService;
import cleverton.heusner.port.output.provider.author.AuthorWithBookProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorWithBookServiceConfiguration {

    @Bean
    public AuthorWithBookService authorWithBookService(@Autowired final AuthorWithBookProvider authorWithBookProvider) {
        return new AuthorWithBookServiceImpl(authorWithBookProvider);
    }
}