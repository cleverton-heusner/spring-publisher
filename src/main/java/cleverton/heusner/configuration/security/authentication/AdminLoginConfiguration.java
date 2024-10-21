package cleverton.heusner.configuration.security.authentication;

import cleverton.heusner.model.Login;
import cleverton.heusner.service.login.LoginService;
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

    private Login createAdminLogin(final PasswordEncoder passwordEncoder) {
        final var adminLogin = new Login();
        adminLogin.setUsername(username);
        adminLogin.setPassword(passwordEncoder.encode(password));
        adminLogin.setRole(role);

        return adminLogin;
    }
}