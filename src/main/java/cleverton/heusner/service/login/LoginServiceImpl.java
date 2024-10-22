package cleverton.heusner.service.login;

import cleverton.heusner.model.Login;
import cleverton.heusner.repository.user.LoginRepository;
import cleverton.heusner.service.message.MessageService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static cleverton.heusner.constant.message.LoginMessage.NOT_FOUND_LOGIN_USERNAME;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository;
    private final MessageService messageService;

    public LoginServiceImpl(final LoginRepository loginRepository,
                            final MessageService messageService) {
        this.loginRepository = loginRepository;
        this.messageService = messageService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        final Login login = loginRepository.findByUsername(username);
        if (login == null) {
            throw new UsernameNotFoundException(messageService.getMessage(NOT_FOUND_LOGIN_USERNAME, username));
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