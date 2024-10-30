package cleverton.heusner.adapter.output.repository;

import cleverton.heusner.adapter.output.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    Optional<AuthorEntity> findByNameIgnoreCase(final String name);
}