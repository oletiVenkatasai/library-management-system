package com.library.config;

import com.library.entity.Role;
import com.library.entity.User;
import com.library.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createUserIfNotExists("admin",  "password", Role.ROLE_ADMIN);

        System.out.println("=================================================");
        System.out.println("  Default user created:");
        System.out.println("  admin  / password  (ADMIN)");
        System.out.println("=================================================");
    }

    private void createUserIfNotExists(String username, String rawPassword, Role role) {
        if (!userRepository.existsByUsername(username)) {
            User user = new User(username, passwordEncoder.encode(rawPassword), role);
            userRepository.save(user);
        }
    }
}
