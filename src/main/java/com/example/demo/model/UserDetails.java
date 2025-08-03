package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "User details information")
public record UserDetails(

        @NotNull
        @Schema(description = "User name", example = "john_doe")
        String name,

        @NotNull
        @Schema(description = "User password", example = "password123")
        String password,

        @NotNull
        @Schema(description = "User role", example = "Admin")
        String userRole

) {
}
