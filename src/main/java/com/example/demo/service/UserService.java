package com.example.demo.service;

import com.example.demo.model.UserDetails;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDetails createUser(UserDetails user) {
        log.info("Creating user: {}", user.name());
        String encryptedPassword = passwordEncoder.encode(user.password());
        UserDetails userWithEncryptedPassword = new UserDetails(user.name(), encryptedPassword, user.userRole(), user.active());
        UserDetails savedUser = userRepository.save(userWithEncryptedPassword);
        log.info("User created successfully: {}", savedUser.name());
        return savedUser;
    }

    public Optional<UserDetails> getUserByName(String name) {
        log.debug("Fetching user by name: {}", name);
        Optional<UserDetails> user = userRepository.findByName(name);
        if (user.isPresent()) {
            log.debug("User found: {}", name);
        } else {
            log.warn("User not found: {}", name);
        }
        return user;
    }

    public List<UserDetails> getAllUsers() {
        log.info("Fetching all users");
        List<UserDetails> users = userRepository.findAll();
        log.info("Retrieved {} users", users.size());
        return users;
    }

    public UserDetails updateUser(String name, UserDetails updatedUser) {
        log.info("Updating user: {}", name);
        String encryptedPassword = passwordEncoder.encode(updatedUser.password());
        UserDetails user = userRepository.save(new UserDetails(name, encryptedPassword, updatedUser.userRole(), updatedUser.active()));
        log.info("User updated successfully: {}", name);
        return user;
    }

    public boolean deleteUser(String name) {
        log.info("Attempting to delete user: {}", name);
        if (userRepository.existsByName(name)) {
            userRepository.deleteByName(name);
            log.info("User deleted successfully: {}", name);
            return true;
        }
        log.warn("User not found for deletion: {}", name);
        return false;
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}