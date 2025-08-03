package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to update user details")
public record UpdateUserRequest(
        @Schema(description = "Authentication credentials")
        AuthRequest auth,
        
        @Schema(description = "Updated user details")
        UserDetails user
) {
}