package com.itacademy.cliagenda.common.validator;

import com.itacademy.cliagenda.common.exception.ValidationException;

/**
 * Validador para entidades de tipo Note.
 * Proporciona métodos de validación para el cuerpo de una nota y su clave foránea de tarea.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class NoteValidator {

    /**
     * Valida el cuerpo de una nota.
     * El cuerpo no puede ser null, vacío ni exceder los 250 caracteres.
     *
     * @param body Contenido de la nota a validar
     * @throws ValidationException si el body es null, vacío o excede 250 caracteres
     */
    public void validateBody(String body) {
        if (body == null || body.trim().isEmpty()) {
            throw new ValidationException("Note body cannot be empty");
        }
        if (body.length() > 250) {
            throw new ValidationException("Note body exceeds maximum length of 250 characters");
        }
    }

    /**
     * Valida la clave foránea de tarea asociada a una nota.
     * El task_fk no puede ser negativo (null es válido).
     *
     * @param taskFk ID de la tarea a validar
     * @throws ValidationException si taskFk es negativo
     */
    public void validateTaskFk(Integer taskFk) {
        if (taskFk != null && taskFk < 0) {
            throw new ValidationException("Task FK cannot be negative");
        }
    }
}