package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Table("note")
@Schema(description = "Note entity for storing user notes")
public class Note {

    @Id
    @Schema(description = "Unique identifier for the note", example = "1")
    private Long noteId;

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Title of the note", example = "Meeting Notes")
    private String title;
    
    @Size(max = 5000)
    @Schema(description = "Content of the note", example = "Discussed project requirements")
    private String content;
    
    @Schema(description = "Creation timestamp", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "Last update timestamp", example = "2024-01-15T14:45:00")
    private LocalDateTime updatedAt;
    
    @Size(max = 255)
    @Schema(description = "Name of the note owner", example = "John Doe")
    private String ownerName;
    
    @Email
    @Size(max = 255)
    @Schema(description = "Email of the note owner", example = "john.doe@example.com")
    private String ownerEmail;

}
