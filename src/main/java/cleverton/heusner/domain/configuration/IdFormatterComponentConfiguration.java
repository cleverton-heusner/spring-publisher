package cleverton.heusner.domain.configuration;

import cleverton.heusner.domain.utils.IdFormatterComponentImpl;
import cleverton.heusner.port.input.utils.IdFormatterComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdFormatterComponentConfiguration {

    @Bean
    public IdFormatterComponent idFormatterComponent() {
        return new IdFormatterComponentImpl();
    }
}