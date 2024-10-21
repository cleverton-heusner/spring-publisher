package cleverton.heusner.service.login;

import cleverton.heusner.model.Login;
import cleverton.heusner.repository.user.LoginRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository;

    public LoginServiceImpl(final LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        final Login login = loginRepository.findByUsername(username);
        if (login == null) {
            throw new UsernameNotFoundException(String.format(
                    "Login '%s' not found.",
                    username)
            );
        }

        return User.withUsername(login.getUsername())
                .password(login.getPassword())
                .roles(login.getRole())
                .build();
    }

    @Override
    public void save(final Login login) {
        loginRepository.save(login);
    }
}