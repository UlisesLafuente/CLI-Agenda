package com.itacademy.cliagenda.common.validator;

import com.itacademy.cliagenda.common.exception.ValidationException;

/**
 * Validador para entidades de tipo Task.
 * Proporciona métodos de validación para el cuerpo de una tarea y su clave foránea de evento.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class TaskValidator {

    /**
     * Valida el cuerpo de una tarea.
     * El cuerpo no puede ser null, vacío ni exceder los 250 caracteres.
     *
     * @param body Contenido de la tarea a validar
     * @throws ValidationException si el body es null, vacío o excede 250 caracteres
     */
    public void validateBody(String body) {
        if (body == null || body.trim().isEmpty()) {
            throw new ValidationException("Task body cannot be empty");
        }
        if (body.length() > 250) {
            throw new ValidationException("Task body exceeds maximum length of 250 characters");
        }
    }

    /**
     * Valida la clave foránea de evento asociada a una tarea.
     * El event_fk no puede ser negativo (null es válido).
     *
     * @param eventFk ID del evento a validar
     * @throws ValidationException si eventFk es negativo
     */
    public void validateEventFk(Integer eventFk) {
        if (eventFk != null && eventFk < 0) {
            throw new ValidationException("Event FK cannot be negative");
        }
    }
}