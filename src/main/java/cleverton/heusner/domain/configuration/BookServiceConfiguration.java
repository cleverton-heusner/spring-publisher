package cleverton.heusner.domain.configuration;

import cleverton.heusner.domain.service.book.BookServiceImpl;
import cleverton.heusner.port.input.utils.IdFormatterComponent;
import cleverton.heusner.port.output.provider.book.BookProvider;
import cleverton.heusner.port.shared.MessageComponent;
import cleverton.heusner.port.input.service.author.AuthorService;
import cleverton.heusner.port.input.service.author.AuthorWithBookService;
import cleverton.heusner.port.input.service.book.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookServiceConfiguration {

    @Bean
    public BookService bookService(final BookProvider bookProvider,
                                   final AuthorWithBookService authorWithBookService,
                                   final AuthorService authorService,
                                   final MessageComponent messageComponent,
                                   final IdFormatterComponent idFormatterComponent) {
        return new BookServiceImpl(
                bookProvider,
                authorWithBookService,
                authorService,
                messageComponent,
                idFormatterComponent
        );
    }
}
