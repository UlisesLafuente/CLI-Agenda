package com.itacademy.cliagenda.event.dto;

import com.itacademy.cliagenda.common.exception.ValidationException;

import java.time.LocalDateTime;

public record UpdateEventRequest(String title, String description, LocalDateTime dateTimeEvent, Boolean recurring, Boolean annualRecurring, Integer recurrenceInterval) {
    public UpdateEventRequest {
        if (title != null && title.trim().isEmpty()) {
            throw new ValidationException("Event title cannot be empty if provided");
        }
        if (title != null && title.length() >= 100) {
            throw new ValidationException("Event title must be shorter than 100 characters");
        }
        if (description != null && description.length() >= 500) {
            throw new ValidationException("Description must be shorter than 500 characters");
        }
        if (recurrenceInterval != null && recurrenceInterval < 0) {
            throw new ValidationException("Recurrence interval cannot be negative");
        }
        if (annualRecurring != null && annualRecurring && recurrenceInterval != null && recurrenceInterval > 0) {
            throw new ValidationException("Cannot set both annual recurring and recurrence interval");
        }
        boolean hasAtLeastOne = (title != null || description != null || dateTimeEvent != null 
                || recurring != null || annualRecurring != null || recurrenceInterval != null);
        if (!hasAtLeastOne) {
            throw new ValidationException("At least one field must be provided for update");
        }
    }
}