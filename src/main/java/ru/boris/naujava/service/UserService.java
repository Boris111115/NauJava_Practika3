package ru.boris.naujava.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.boris.naujava.entity.Role;
import ru.boris.naujava.entity.User;
import ru.boris.naujava.repository.UserRepository;

import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(String username, String rawPassword) {
        String normalizedUsername = username == null ? "" : username.trim();
        if (normalizedUsername.isBlank()) {
            throw new IllegalArgumentException("Укажите имя пользователя");
        }
        if (normalizedUsername.length() > 50) {
            throw new IllegalArgumentException("Имя пользователя не должно превышать 50 символов");
        }
        if (rawPassword == null || rawPassword.length() < 8) {
            throw new IllegalArgumentException("Пароль должен содержать не менее 8 символов");
        }
        if (userRepository.existsByUsername(normalizedUsername)) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }
        User user = new User();
        user.setUsername(normalizedUsername);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(Set.of(Role.USER));
        return userRepository.save(user);
    }
}
