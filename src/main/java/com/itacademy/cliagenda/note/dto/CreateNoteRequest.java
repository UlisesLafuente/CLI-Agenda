package com.itacademy.cliagenda.note.dto;

import com.itacademy.cliagenda.common.exception.ValidationException;

/**
 * DTO for creating a new note.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public record CreateNoteRequest(String body, Integer taskId) {
    public CreateNoteRequest {
        if (body == null || body.trim().isEmpty()) {
            throw new ValidationException("Note body cannot be empty");
        }
        if (body.length() > 250) {
            throw new ValidationException("Note body cannot exceed 250 characters");
        }
        if (taskId == null || taskId < 1) {
            throw new ValidationException("Task ID is required and must be positive");
        }
    }
}