package com.itacademy.cliagenda.common.validator;

import com.itacademy.cliagenda.common.exception.ValidationException;

public class TaskValidator {

    public void validateBody(String body) {
        if (body == null || body.trim().isEmpty()) {
            throw new ValidationException("Task body cannot be empty");
        }
        if (body.length() > 250) {
            throw new ValidationException("Task body exceeds maximum length of 250 characters");
        }
    }

    public void validateEventFk(Integer eventFk) {
        if (eventFk != null && eventFk < 0) {
            throw new ValidationException("Event FK cannot be negative");
        }
    }
}