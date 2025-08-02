package com.example.demo.service;

import com.example.demo.model.Note;
import com.example.demo.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {

    private final NoteRepository noteRepository;

    private static final String MESSAGE = "Note not found with id: ";

    public Flux<Note> getAllNotes() {
        log.info("Fetching all notes from repository");
        return noteRepository.findAll()
                .doOnNext(note -> log.debug("Retrieved note: {}", note.getNoteId()))
                .doOnComplete(() -> log.info("All notes retrieved successfully"));
    }

    public Mono<Note> get(long noteId) {
        log.info("Fetching note with id: {}", noteId);
        return noteRepository.findById(noteId)
                .doOnNext(note -> log.debug("Note found: {}", noteId))
                .switchIfEmpty(Mono.<Note>error(new RuntimeException(MESSAGE + noteId))
                        .doOnError(error -> log.warn("Note not found: {}", noteId)));
    }

    public Mono<Note> create(Note note) {
        log.info("Creating new note with title: {}", note.getTitle());
        return noteRepository.save(note)
                .doOnNext(savedNote -> log.info("Note created with id: {}", savedNote.getNoteId()));
    }

    public Flux<Note> createBulk(Flux<Note> notes) {
        log.info("Creating notes in bulk");
        return noteRepository.saveAll(notes)
                .doOnNext(savedNote -> log.debug("Bulk note created with id: {}", savedNote.getNoteId()))
                .doOnComplete(() -> log.info("Bulk notes creation completed"));
    }

    public Mono<Note> update(long noteId, Note note) {
        log.info("Updating note with id: {}", noteId);
        return noteRepository.findById(noteId)
                .flatMap(existingNote -> {
                    log.debug("Found existing note for update: {}", noteId);
                    existingNote.setTitle(note.getTitle());
                    existingNote.setContent(note.getContent());
                    existingNote.setUpdatedAt(note.getUpdatedAt());
                    return noteRepository.save(existingNote);
                })
                .doOnNext(updatedNote -> log.info("Note updated successfully: {}", noteId))
                .switchIfEmpty(Mono.<Note>error(new RuntimeException(MESSAGE + noteId))
                        .doOnError(error -> log.warn("Note not found for update: {}", noteId)));
    }

    public Mono<Note> patch(long noteId, Note note) {
        log.info("Patching note with id: {}", noteId);
        return noteRepository.findById(noteId)
                .flatMap(existingNote -> {
                    log.debug("Found existing note for patch: {}", noteId);
                    if (note.getTitle() != null) existingNote.setTitle(note.getTitle());
                    if (note.getContent() != null) existingNote.setContent(note.getContent());
                    if (note.getUpdatedAt() != null) existingNote.setUpdatedAt(note.getUpdatedAt());
                    if (note.getOwnerName() != null) existingNote.setOwnerName(note.getOwnerName());
                    if (note.getOwnerEmail() != null) existingNote.setOwnerEmail(note.getOwnerEmail());
                    return noteRepository.save(existingNote);
                })
                .doOnNext(patchedNote -> log.info("Note patched successfully: {}", noteId))
                .switchIfEmpty(Mono.<Note>error(new RuntimeException(MESSAGE + noteId))
                        .doOnError(error -> log.warn("Note not found for patch: {}", noteId)));
    }

    public Mono<Void> delete(long noteId) {
        log.info("Deleting note with id: {}", noteId);
        return noteRepository.deleteById(noteId)
                .doOnSuccess(result -> log.info("Note deleted successfully: {}", noteId));
    }
}
