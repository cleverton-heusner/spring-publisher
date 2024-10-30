package cleverton.heusner.port.input.service.login;

import cleverton.heusner.adapter.output.entity.LoginEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginService extends UserDetailsService {
    void save(final LoginEntity loginEntity);
}