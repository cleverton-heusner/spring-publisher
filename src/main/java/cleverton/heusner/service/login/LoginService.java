package cleverton.heusner.service.login;

import cleverton.heusner.model.Login;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginService extends UserDetailsService {
    void save(final Login login);
}