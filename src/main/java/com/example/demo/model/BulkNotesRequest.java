package com.example.demo.model;

import reactor.core.publisher.Flux;

public record BulkNotesRequest(
        AuthRequest auth,
        Flux<Note> notes
) {
}