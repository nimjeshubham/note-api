package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to create a new note")
public record CreateNoteRequest(
        @NotNull
        @Valid
        @Schema(description = "Authentication credentials")
        AuthRequest auth,
        
        @NotNull
        @Valid
        @Schema(description = "Note details to create")
        Note note
) {
}