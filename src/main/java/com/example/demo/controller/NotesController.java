package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.NoteService;
import com.example.demo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("api/v1/notes")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Notes Management", description = "APIs for managing notes with reactive programming")
@CrossOrigin(origins = "*", maxAge = 3600)
public class NotesController {

    private final NoteService noteService;
    private final AuthService authService;

    private ResponseEntity<?> validateAuth(AuthRequest auth) {
        if (authService.validateUser(auth.username(), auth.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        return null;
    }


    @GetMapping
    @Operation(summary = "Get all notes", description = "Retrieves all notes using reactive streams")
    @ApiResponse(responseCode = "200", description = "Notes retrieved successfully")
    public ResponseEntity<?> getAllNotes(@RequestBody AuthRequest auth) {
        ResponseEntity<?> authError = validateAuth(auth);
        if (authError != null) return authError;
        
        log.info("GET /api/v1/notes - Fetching all notes for user: {}", auth.username());
        Flux<Note> notes = noteService.getAllNotes();
        log.info("GET /api/v1/notes - Notes retrieved successfully");
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get note by ID", description = "Retrieves a specific note by its ID using reactive mono")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note found and retrieved"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    public ResponseEntity<?> getNoteById(
            @Parameter(description = "ID of the note to retrieve", example = "1") @PathVariable long id,
            @RequestBody AuthRequest auth) {
        ResponseEntity<?> authError = validateAuth(auth);
        if (authError != null) return authError;
        
        log.info("GET /api/v1/notes/{} - Fetching note for user: {}", id, auth.username());
        Mono<Note> note = noteService.get(id);
        log.info("GET /api/v1/notes/{} - Note retrieved successfully", id);
        return ResponseEntity.ok(note);
    }

    @PostMapping
    @Operation(summary = "Create a new note", description = "Creates a new note with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Note created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid note data")
    })
    public ResponseEntity<?> createNote(@RequestBody CreateNoteRequest request) {
        ResponseEntity<?> authError = validateAuth(request.auth());
        if (authError != null) return authError;
        
        log.info("POST /api/v1/notes - Creating new note for user: {}", request.auth().username());
        Mono<Note> createdNote = noteService.create(request.note());
        log.info("POST /api/v1/notes - Note created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create multiple notes", description = "Creates multiple notes in bulk using reactive flux")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notes created successfully in bulk"),
            @ApiResponse(responseCode = "400", description = "Invalid notes data")
    })
    public ResponseEntity<?> createNotesBulk(@RequestBody BulkNotesRequest request) {
        ResponseEntity<?> authError = validateAuth(request.auth());
        if (authError != null) return authError;
        
        log.info("POST /api/v1/notes/bulk - Creating notes in bulk for user: {}", request.auth().username());
        Flux<Note> createdNotes = noteService.createBulk(request.notes());
        log.info("POST /api/v1/notes/bulk - Bulk notes created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update note", description = "Updates an existing note completely with new data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note updated successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    public ResponseEntity<?> updateNote(
            @Parameter(description = "ID of the note to update", example = "1") @PathVariable long id,
            @RequestBody UpdateNoteRequest request) {
        ResponseEntity<?> authError = validateAuth(request.auth());
        if (authError != null) return authError;
        
        log.info("PUT /api/v1/notes/{} - Updating note for user: {}", id, request.auth().username());
        Mono<Note> updatedNote = noteService.update(id, request.note());
        log.info("PUT /api/v1/notes/{} - Note updated successfully", id);
        return ResponseEntity.ok(updatedNote);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update note", description = "Updates specific fields of an existing note")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note partially updated successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    public ResponseEntity<?> patchNote(
            @Parameter(description = "ID of the note to partially update", example = "1") @PathVariable long id,
            @RequestBody UpdateNoteRequest request) {
        ResponseEntity<?> authError = validateAuth(request.auth());
        if (authError != null) return authError;
        
        log.info("PATCH /api/v1/notes/{} - Partially updating note for user: {}", id, request.auth().username());
        Mono<Note> patchedNote = noteService.patch(id, request.note());
        log.info("PATCH /api/v1/notes/{} - Note patched successfully", id);
        return ResponseEntity.ok(patchedNote);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete note", description = "Deletes a note by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    public ResponseEntity<?> deleteNote(
            @Parameter(description = "ID of the note to delete", example = "1") @PathVariable long id,
            @RequestBody AuthRequest auth) {
        ResponseEntity<?> authError = validateAuth(auth);
        if (authError != null) return authError;
        
        log.info("DELETE /api/v1/notes/{} - Deleting note for user: {}", id, auth.username());
        Mono<Void> result = noteService.delete(id);
        log.info("DELETE /api/v1/notes/{} - Note deleted successfully", id);
        return ResponseEntity.ok(result);
    }

}
