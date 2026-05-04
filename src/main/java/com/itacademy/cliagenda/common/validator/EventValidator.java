package com.itacademy.cliagenda.common.validator;

import com.itacademy.cliagenda.common.exception.ValidationException;

/**
 * Validador para entidades de tipo Event.
 * Proporciona métodos de validación para el título y descripción de un evento.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class EventValidator {

    /**
     * Valida el título de un evento.
     * El título no puede ser null, vacío ni exceder los 100 caracteres.
     *
     * @param title Título del evento a validar
     * @throws ValidationException si el título es null, vacío o excede 100 caracteres
     */
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

    /**
     * Valida la descripción de un evento.
     * La descripción no puede exceder los 500 caracteres (puede ser null).
     *
     * @param description Descripción del evento a validar
     * @throws ValidationException si la descripción excede 500 caracteres
     */
    public void validateDescription(String description) {
        if (description != null && description.length() >= 500) {
            throw new ValidationException("Description must be shorter than 500 characters");
        }
    }
}