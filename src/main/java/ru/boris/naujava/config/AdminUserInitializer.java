package ru.boris.naujava.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.boris.naujava.entity.Role;
import ru.boris.naujava.entity.User;
import ru.boris.naujava.repository.UserRepository;

import java.util.Set;

@Component
public class AdminUserInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminUsername;
    private final String adminPassword;

    public AdminUserInitializer(UserRepository userRepository,
                                PasswordEncoder passwordEncoder,
                                @Value("${app.admin.username:admin}") String adminUsername,
                                @Value("${app.admin.password:ChangeMe123!}") String adminPassword) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) {
        if (adminPassword == null || adminPassword.length() < 8) {
            throw new IllegalStateException("app.admin.password должен содержать не менее 8 символов");
        }
        if (!userRepository.existsByUsername(adminUsername)) {
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRoles(Set.of(Role.ADMIN));
            userRepository.save(admin);
        }
    }
}
