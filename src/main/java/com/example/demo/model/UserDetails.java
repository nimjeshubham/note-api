package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "User details information")
public record UserDetails(

        @NotBlank
        @Size(min = 3, max = 50)
        @Schema(description = "User name", example = "john_doe")
        String name,

        @NotBlank
        @Size(min = 8, max = 100)
        @Schema(description = "User password", example = "[HIDDEN]", accessMode = Schema.AccessMode.WRITE_ONLY)
        String password,

        @NotNull
        @Schema(description = "User role", example = "ADMIN")
        AppRole userRole,

        @NotNull
        @Schema(description = "User active status", example = "true")
        Boolean active

) {
}
