package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.UserService;
import com.example.demo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("api/v1/user")
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Management", description = "APIs for managing user details")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    private ResponseEntity<?> validateAuth(AuthRequest auth) {
        if (!authService.validateUser(auth.username(), auth.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        return null;
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data")
    })
    public ResponseEntity<UserDetails> createUser(@Valid @RequestBody UserDetails user) {
        log.info("POST /api/v1/user - Creating user: {}", user.name());
        UserDetails createdUser = userService.createUser(user);
        log.info("POST /api/v1/user - User created successfully: {}", createdUser.name());
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{name}")
    @Operation(summary = "Get user by name", description = "Retrieves a user by their name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> getUser(
            @Parameter(description = "Name of the user to retrieve") @PathVariable String name,
            @Valid @RequestBody AuthRequest auth) {
        ResponseEntity<?> authError = validateAuth(auth);
        if (authError != null) return authError;
        
        log.info("GET /api/v1/user/{} - Retrieving user", name);
        return userService.getUserByName(name)
                .map(user -> {
                    log.info("GET /api/v1/user/{} - User found", name);
                    return ResponseEntity.ok(user);
                })
                .orElseGet(() -> {
                    log.warn("GET /api/v1/user/{} - User not found", name);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    public ResponseEntity<?> getAllUsers(@Valid @RequestBody AuthRequest auth) {
        ResponseEntity<?> authError = validateAuth(auth);
        if (authError != null) return authError;
        
        log.info("GET /api/v1/user - Retrieving all users");
        List<UserDetails> users = userService.getAllUsers();
        log.info("GET /api/v1/user - Retrieved {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{name}")
    @Operation(summary = "Update user", description = "Updates an existing user's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> updateUser(
            @Parameter(description = "Name of the user to update") @PathVariable String name, 
            @Valid @RequestBody UpdateUserRequest request) {
        ResponseEntity<?> authError = validateAuth(request.auth());
        if (authError != null) return authError;
        
        log.info("PUT /api/v1/user/{} - Updating user", name);
        if (!userService.getUserByName(name).isPresent()) {
            log.warn("PUT /api/v1/user/{} - User not found for update", name);
            return ResponseEntity.notFound().build();
        }
        UserDetails updatedUser = userService.updateUser(name, request.user());
        log.info("PUT /api/v1/user/{} - User updated successfully", name);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "Delete user", description = "Deletes a user by their name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> deleteUser(
            @Parameter(description = "Name of the user to delete") @PathVariable String name,
            @Valid @RequestBody AuthRequest auth) {
        ResponseEntity<?> authError = validateAuth(auth);
        if (authError != null) return authError;
        
        log.info("DELETE /api/v1/user/{} - Deleting user", name);
        boolean deleted = userService.deleteUser(name);
        if (deleted) {
            log.info("DELETE /api/v1/user/{} - User deleted successfully", name);
            return ResponseEntity.ok().build();
        } else {
            log.warn("DELETE /api/v1/user/{} - User not found for deletion", name);
            return ResponseEntity.notFound().build();
        }
    }
}
