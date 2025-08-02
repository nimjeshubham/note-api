package com.example.demo.repository;

import com.example.demo.model.Note;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends ReactiveCrudRepository<Note, Long> {
}
