package cleverton.heusner.repository.author;

import cleverton.heusner.adapter.output.repository.AuthorRepository;
import cleverton.heusner.adapter.output.entity.AuthorEntity;
import org.instancio.Instancio;
import org.instancio.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AuthorRepositoryTestConfiguration {

    @Autowired
    protected AuthorRepository authorRepository;

    protected AuthorEntity findAuthorById(final long id) {
        return authorRepository.findById(id).orElse(null);
    }

    protected AuthorEntity createAuthorWithoutBook() {
        return authorRepository.save(Instancio.of(AuthorEntity.class)
                .set(Select.field(AuthorEntity::getBook), null)
                .create());
    }
}
