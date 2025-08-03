package com.example.demo.controller;

import com.example.demo.model.AuthRequest;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final AuthService authService;

    private ResponseEntity<?> validateAuth(AuthRequest auth) {
        if (authService.validateUser(auth.username(), auth.password())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        return null;
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello(@RequestBody AuthRequest auth) {
        ResponseEntity<?> authError = validateAuth(auth);
        if (authError != null) return authError;
        
        log.info("GET /test/hello - Request received for user: {}", auth.username());
        String response = "Hello, World!";
        log.info("GET /test/hello - Response sent: {}", response);
        return ResponseEntity.ok(response);
    }
}
