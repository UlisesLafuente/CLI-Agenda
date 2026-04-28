package com.itacademy.cliagenda.task.dto;

import com.itacademy.cliagenda.common.exception.ValidationException;

/**
 * DTO for creating a new task.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public record CreateTaskRequest(String body, Integer eventId) {
    public CreateTaskRequest {
        if (body == null || body.trim().isEmpty()) {
            throw new ValidationException("Task body cannot be empty");
        }
        if (body.length() > 250) {
            throw new ValidationException("Task body cannot exceed 250 characters");
        }
        if (eventId != null && eventId < 0) {
            throw new ValidationException("Event ID cannot be negative");
        }
    }
}