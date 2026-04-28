package com.itacademy.cliagenda.common.exception;

/**
 * Excepción para errores de base de datos.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class DatabaseException extends RuntimeException {

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}