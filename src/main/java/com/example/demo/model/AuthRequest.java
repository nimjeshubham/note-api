package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Authentication request")
public record AuthRequest(
        @NotBlank
        @Size(min = 3, max = 50)
        @Schema(description = "Username", example = "admin")
        String username,
        
        @NotBlank
        @Size(min = 8, max = 100)
        @Schema(description = "Password", example = "[HIDDEN]", accessMode = Schema.AccessMode.WRITE_ONLY)
        String password
) {
}