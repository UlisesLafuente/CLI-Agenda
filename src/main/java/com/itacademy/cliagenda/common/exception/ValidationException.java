package com.itacademy.cliagenda.common.exception;

/**
 * Excepción para errores de validación.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}