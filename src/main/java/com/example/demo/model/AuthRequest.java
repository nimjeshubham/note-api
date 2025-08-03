package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Authentication request")
public record AuthRequest(
        @NotNull
        @Schema(description = "Username", example = "admin")
        String username,
        
        @NotNull
        @Schema(description = "Password", example = "[HIDDEN]", accessMode = Schema.AccessMode.WRITE_ONLY)
        String password
) {
}