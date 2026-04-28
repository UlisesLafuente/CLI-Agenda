package com.itacademy.cliagenda.note.dto;

import com.itacademy.cliagenda.common.exception.ValidationException;

/**
 * DTO for updating an existing note.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public record UpdateNoteRequest(String body, Integer taskId) {
    public UpdateNoteRequest {
        if (body != null && body.trim().isEmpty()) {
            throw new ValidationException("Note body cannot be empty if provided");
        }
        if (body != null && body.length() > 250) {
            throw new ValidationException("Note body cannot exceed 250 characters");
        }
        if (taskId != null && taskId < 0) {
            throw new ValidationException("Task ID cannot be negative");
        }
        if (body == null && taskId == null) {
            throw new ValidationException("At least one field must be provided for update");
        }
    }
}