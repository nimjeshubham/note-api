package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
@Schema(description = "Note entity for storing user notes")
public class Note {

    @Id
    @Schema(description = "Unique identifier for the note", example = "1")
    private long noteId;

    @NotNull
    @Schema(description = "Title of the note", example = "Meeting Notes")
    private String title;
    
    @Schema(description = "Content of the note", example = "Discussed project requirements")
    private String content;
    
    @Schema(description = "Creation timestamp", example = "2024-01-15T10:30:00")
    private String createdAt;
    
    @Schema(description = "Last update timestamp", example = "2024-01-15T14:45:00")
    private String updatedAt;
    
    @Schema(description = "Name of the note owner", example = "John Doe")
    private String ownerName;
    
    @Schema(description = "Email of the note owner", example = "john.doe@example.com")
    private String ownerEmail;

}
