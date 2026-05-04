package com.itacademy.cliagenda.common.validator;

import com.itacademy.cliagenda.common.exception.ValidationException;

public class EventValidator {

    public void validateTitle(String title) {
        if (title == null) {
            throw new ValidationException("Title cannot be null");
        }
        if (title.trim().isEmpty()) {
            throw new ValidationException("Title cannot be empty");
        }
        if (title.length() >= 100) {
            throw new ValidationException("Title must be shorter than 100 characters");
        }
    }

    public void validateDescription(String description) {
        if (description != null && description.length() >= 500) {
            throw new ValidationException("Description must be shorter than 500 characters");
        }
    }
}