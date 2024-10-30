package cleverton.heusner.domain.service.author;

import cleverton.heusner.domain.model.Author;
import cleverton.heusner.domain.service.ServiceConfiguration;
import cleverton.heusner.port.input.utils.IdFormatterComponent;
import cleverton.heusner.port.output.provider.author.AuthorProvider;
import org.instancio.Instancio;
import org.instancio.Select;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;

public class AuthorServiceTestConfiguration extends ServiceConfiguration {

    protected static final String AUTHOR_NOT_FOUND_BY_ID_MESSAGE = "Author with id '%d' not found.";

    @Mock
    protected AuthorProvider authorProvider;

    @Mock
    protected IdFormatterComponent idFormatterComponent;

    @InjectMocks
    protected AuthorServiceImpl authorServiceImpl;

    protected Author getValidAuthor() {
        return Instancio.of(Author.class)
                .set(Select.field(Author::getBirthDate), LocalDate.now())
                .create();
    }
}