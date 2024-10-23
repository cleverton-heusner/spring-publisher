package cleverton.heusner.service.author;

import cleverton.heusner.repository.author.AuthorRepository;
import cleverton.heusner.service.idformatter.IdFormatterService;
import cleverton.heusner.service.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class AuthorServiceTestConfiguration extends ServiceConfiguration {

    protected static final String AUTHOR_NOT_FOUND_BY_ID_MESSAGE = "Author with id '%d' not found.";

    @Mock
    protected AuthorRepository authorRepository;

    @Mock
    protected IdFormatterService idFormatterService;

    @InjectMocks
    protected AuthorServiceImpl authorServiceImpl;
}