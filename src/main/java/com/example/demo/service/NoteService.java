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
        return noteRepository.findAll();
    }

    public Mono<Note> get(long noteId) {
        return noteRepository.findById(noteId)
                .switchIfEmpty(Mono.error(new RuntimeException(MESSAGE + noteId)));
    }

    public Mono<Note> create(Note note) {
        return noteRepository.save(note);
    }

    public Flux<Note> createBulk(Flux<Note> notes) {
        return noteRepository.saveAll(notes);
    }

    public Mono<Note> update(long noteId, Note note) {
        return noteRepository.findById(noteId)
                .flatMap(existingNote -> {
                    existingNote.setTitle(note.getTitle());
                    existingNote.setContent(note.getContent());
                    existingNote.setUpdatedAt(note.getUpdatedAt());
                    return noteRepository.save(existingNote);
                })
                .switchIfEmpty(Mono.error(new RuntimeException(MESSAGE + noteId)));
    }

    public Mono<Note> patch(long noteId, Note note) {
        return noteRepository.findById(noteId)
                .flatMap(existingNote -> {
                    if (note.getTitle() != null) existingNote.setTitle(note.getTitle());
                    if (note.getContent() != null) existingNote.setContent(note.getContent());
                    if (note.getUpdatedAt() != null) existingNote.setUpdatedAt(note.getUpdatedAt());
                    if (note.getOwnerName() != null) existingNote.setOwnerName(note.getOwnerName());
                    if (note.getOwnerEmail() != null) existingNote.setOwnerEmail(note.getOwnerEmail());
                    return noteRepository.save(existingNote);
                })
                .switchIfEmpty(Mono.error(new RuntimeException(MESSAGE + noteId)));
    }

    public Mono<Void> delete(long noteId) {
        return noteRepository.deleteById(noteId);
    }
}
