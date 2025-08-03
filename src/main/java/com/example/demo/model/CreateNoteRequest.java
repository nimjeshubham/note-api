package com.example.demo.model;

public record CreateNoteRequest(
        AuthRequest auth,
        Note note
) {
}