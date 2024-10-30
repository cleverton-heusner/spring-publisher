package cleverton.heusner.adapter.output.repository;

import cleverton.heusner.adapter.output.entity.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<LoginEntity, Long> {
    LoginEntity findByUsername(final String username);
}
