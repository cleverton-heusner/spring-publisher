package cleverton.heusner.repository.user;

import cleverton.heusner.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findByUsername(final String username);
}
