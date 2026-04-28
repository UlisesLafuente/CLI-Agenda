package com.itacademy.cliagenda.task.dto;

import com.itacademy.cliagenda.common.exception.ValidationException;

public record UpdateTaskRequest(String body, Integer eventId, Boolean completed) {
    public UpdateTaskRequest {
        if (body != null && body.trim().isEmpty()) {
            throw new ValidationException("Task body cannot be empty if provided");
        }
        if (body != null && body.length() > 250) {
            throw new ValidationException("Task body cannot exceed 250 characters");
        }
        if (eventId != null && eventId < 0) {
            throw new ValidationException("Event ID cannot be negative");
        }
        if (body == null && eventId == null && completed == null) {
            throw new ValidationException("At least one field must be provided for update");
        }
    }
}