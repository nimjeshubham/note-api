package com.example.demo.service;

import com.example.demo.model.UserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    public boolean validateUser(String username, String password) {
        return userService.getUserByName(username)
                .map(user -> userService.verifyPassword(password, user.password()))
                .orElse(false);
    }

    public UserDetails authenticateUser(String username, String password) {
        return userService.getUserByName(username)
                .filter(user -> userService.verifyPassword(password, user.password()))
                .orElse(null);
    }
}