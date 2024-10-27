package cleverton.heusner.adapter.input.configuration.security;

import cleverton.heusner.adapter.output.entity.LoginEntity;
import cleverton.heusner.port.input.service.login.LoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminLoginConfiguration {

    @Value("${login.admin.username}")
    private String username;

    @Value("${login.admin.password}")
    private String password;

    @Value("${login.admin.role}")
    private String role;

    @Bean
    public CommandLineRunner createAdminLogin(final LoginService loginService, final PasswordEncoder passwordEncoder) {
        return _ -> loginService.save(createAdminLogin(passwordEncoder));
    }

    private LoginEntity createAdminLogin(final PasswordEncoder passwordEncoder) {
        final var adminLogin = new LoginEntity();
        adminLogin.setUsername(username);
        adminLogin.setPassword(passwordEncoder.encode(password));
        adminLogin.setRole(role);

        return adminLogin;
    }
}