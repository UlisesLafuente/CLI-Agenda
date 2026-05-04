package com.itacademy.cliagenda.note.model;

import com.itacademy.cliagenda.common.exception.ValidationException;

/**
 * Entidad de dominio que representa una nota en la agenda.
 * Una nota debe estar asociada a una tarea.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class Note {
    private final int id;
    private String body;
    private Integer task_fk;

    /**
     * Constructor para crear una nota asociada a una tarea.
     *
     * @param id       Identificador único de la nota
     * @param body     Contenido de la nota
     * @param task_fk  Identificador de la tarea asociada (puede ser null)
     */
    public Note(int id, String body, Integer task_fk) {
        this.id = id;
        this.body = body;
        this.task_fk = task_fk;
    }

    /**
     * Obtiene el identificador de la nota.
     *
     * @return Identificador de la nota
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el contenido de la nota.
     *
     * @return Contenido de la nota
     */
    public String getBody() {
        return body;
    }

    /**
     * Modifica el contenido de la nota.
     * Valida que el body no exceda los 250 caracteres.
     *
     * @param body Nuevo contenido para la nota
     * @throws ValidationException si el body excede los 250 caracteres
     */
    public void changeBody(String body) {
        if (body != null && body.length() > 250) {
            throw new ValidationException("Note body exceeds maximum length of 250 characters");
        }
        this.body = body;
    }

    /**
     * Obtiene el identificador de la tarea asociada.
     *
     * @return Identificador de la tarea o null si no hay tarea asociada
     */
    public Integer getTask_fk() {
        return task_fk;
    }

    /**
     * Asocia la nota a una tarea.
     * Valida que el task_fk no sea negativo.
     *
     * @param task_fk Identificador de la tarea (puede ser null para desvincular)
     * @throws ValidationException si task_fk es negativo
     */
    public void setTask_fk(Integer task_fk) {
        if (task_fk != null && task_fk < 0) {
            throw new ValidationException("Task FK cannot be negative");
        }
        this.task_fk = task_fk;
    }
}
