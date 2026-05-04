package com.itacademy.cliagenda.common.validator;

import com.itacademy.cliagenda.common.exception.ValidationException;

public class NoteValidator {

    public void validateBody(String body) {
        if (body == null || body.trim().isEmpty()) {
            throw new ValidationException("Note body cannot be empty");
        }
        if (body.length() > 250) {
            throw new ValidationException("Note body exceeds maximum length of 250 characters");
        }
    }

    public void validateTaskFk(Integer taskFk) {
        if (taskFk != null && taskFk < 0) {
            throw new ValidationException("Task FK cannot be negative");
        }
    }
}