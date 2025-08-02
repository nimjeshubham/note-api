package com.example.demo.controller;

import com.example.demo.model.Note;
import com.example.demo.model.UserDetails;
import com.example.demo.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("api/v1/notes")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class NotesController {

    private final NoteService noteService;


    @GetMapping
    public ResponseEntity<Flux<Note>> getAllNotes(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Fetching all notes");
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<Note>> getNoteById(@PathVariable long id,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Fetching note with id: {}", id);
        return ResponseEntity.ok(noteService.get(id));
    }

    @PostMapping
    public ResponseEntity<Mono<Note>> createNote(@RequestBody Note note,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Creating new note");
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.create(note));
    }

    @PostMapping("/bulk")
    public ResponseEntity<Flux<Note>> createNotesBulk(@RequestBody Flux<Note> notes,
                                                      @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Creating notes in bulk");
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.createBulk(notes));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mono<Note>> updateNote(@PathVariable long id,
                                                 @RequestBody Note note,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Updating note with id: {}", id);
        return ResponseEntity.ok(noteService.update(id, note));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Mono<Note>> patchNote(@PathVariable long id,
                                                @RequestBody Note note,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Partially updating note with id: {}", id);
        return ResponseEntity.ok(noteService.patch(id, note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> deleteNote(@PathVariable long id,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Deleting note with id: {}", id);
        return ResponseEntity.ok(noteService.delete(id));
    }

}
