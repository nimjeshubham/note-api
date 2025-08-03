package com.example.demo.model;

public record UpdateNoteRequest(
        AuthRequest auth,
        Note note
) {
}