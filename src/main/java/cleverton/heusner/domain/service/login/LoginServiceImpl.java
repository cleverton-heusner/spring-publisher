package cleverton.heusner.domain.service.login;

import cleverton.heusner.adapter.output.entity.LoginEntity;
import cleverton.heusner.adapter.output.repository.LoginRepository;
import cleverton.heusner.port.shared.MessageComponent;
import cleverton.heusner.port.input.service.login.LoginService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static cleverton.heusner.adapter.input.constant.business.LoginBusinessErrorMessage.NOT_FOUND_LOGIN_USERNAME;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository;
    private final MessageComponent messageComponent;

    public LoginServiceImpl(final LoginRepository loginRepository,
                            final MessageComponent messageComponent) {
        this.loginRepository = loginRepository;
        this.messageComponent = messageComponent;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        final LoginEntity loginEntity = loginRepository.findByUsername(username);
        if (loginEntity == null) {
            throw new UsernameNotFoundException(messageComponent.getMessage(NOT_FOUND_LOGIN_USERNAME, username));
        }

        return User.withUsername(loginEntity.getUsername())
                .password(loginEntity.getPassword())
                .roles(loginEntity.getRole())
                .build();
    }

    @Override
    public void save(final LoginEntity loginEntity) {
        loginRepository.save(loginEntity);
    }
}