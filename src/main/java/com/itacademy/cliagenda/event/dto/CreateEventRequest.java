package com.itacademy.cliagenda.event.dto;

import com.itacademy.cliagenda.common.exception.ValidationException;

import java.time.LocalDateTime;

public record CreateEventRequest(String title, String description, LocalDateTime dateTimeEvent, boolean recurring, boolean annualRecurring, int recurrenceInterval) {
    public CreateEventRequest {
        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException("Event title cannot be empty");
        }
        if (title.length() >= 100) {
            throw new ValidationException("Event title must be shorter than 100 characters");
        }
        if (description != null && description.length() >= 500) {
            throw new ValidationException("Description must be shorter than 500 characters");
        }
        if (dateTimeEvent == null) {
            throw new ValidationException("Event date/time cannot be empty");
        }
        if (recurrenceInterval < 0) {
            throw new ValidationException("Recurrence interval cannot be negative");
        }
        if (annualRecurring && recurrenceInterval > 0) {
            throw new ValidationException("Cannot set both annual recurring and recurrence interval");
        }
    }
}