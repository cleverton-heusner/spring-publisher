package cleverton.heusner.repository.author;

import cleverton.heusner.model.Author;
import org.instancio.Instancio;
import org.instancio.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AuthorRepositoryTestConfiguration {

    @Autowired
    protected AuthorRepository authorRepository;

    protected Author findAuthorById(final long id) {
        return authorRepository.findById(id).orElse(null);
    }

    protected Author createAuthorWithoutBook() {
        return authorRepository.save(Instancio.of(Author.class)
                .set(Select.field(Author::getBook), null)
                .create());
    }
}
