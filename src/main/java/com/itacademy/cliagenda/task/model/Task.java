package com.itacademy.cliagenda.task.model;

import com.itacademy.cliagenda.common.exception.ValidationException;

/**
 * Entidad de dominio que representa una tarea en la agenda.
 * Una tarea puede estar asociada a un evento y puede marcarse como completada o no.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class Task {
    private final int id;
    private String body;
    private Integer event_fk;
    private boolean completed;

    /**
     * Constructor básico para crear una tarea sin evento asociado.
     *
     * @param id   Identificador único de la tarea
     * @param body Contenido de la tarea
     */
    public Task(int id, String body) {
        this.id = id;
        this.body = body;
        this.event_fk = null;
        this.completed = false;
    }

    /**
     * Constructor para crear una tarea asociada a un evento.
     *
     * @param id        Identificador único de la tarea
     * @param body      Contenido de la tarea
     * @param event_fk  Identificador del evento asociado (puede ser null)
     */
    public Task(int id, String body, Integer event_fk) {
        this.id = id;
        this.body = body;
        this.event_fk = event_fk;
        this.completed = false;
    }

    /**
     * Constructor completo para crear una tarea con todos los campos.
     *
     * @param id        Identificador único de la tarea
     * @param body      Contenido de la tarea
     * @param event_fk  Identificador del evento asociado (puede ser null)
     * @param completed Indica si la tarea está completada
     */
    public Task(int id, String body, Integer event_fk, boolean completed) {
        this.id = id;
        this.body = body;
        this.event_fk = event_fk;
        this.completed = completed;
    }

    /**
     * Obtiene el identificador de la tarea.
     *
     * @return Identificador de la tarea
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el contenido de la tarea.
     *
     * @return Contenido de la tarea
     */
    public String getBody() {
        return body;
    }

    /**
     * Modifica el contenido de la tarea.
     * Valida que el body no exceda los 250 caracteres.
     *
     * @param body Nuevo contenido para la tarea
     * @throws ValidationException si el body excede los 250 caracteres
     */
    public void changeBody(String body) {
        if (body != null && body.length() > 250) {
            throw new ValidationException("Task body exceeds maximum length of 250 characters");
        }
        this.body = body;
    }

    /**
     * Obtiene el identificador del evento asociado.
     *
     * @return Identificador del evento o null si no hay evento asociado
     */
    public Integer getEvent_fk() {
        return event_fk;
    }

    /**
     * Asocia la tarea a un evento.
     * Valida que el event_fk no sea negativo.
     *
     * @param event_fk Identificador del evento (puede ser null para desvincular)
     * @throws ValidationException si event_fk es negativo
     */
    public void setEvent_fk(Integer event_fk) {
        if (event_fk != null && event_fk < 0) {
            throw new ValidationException("Event FK cannot be negative");
        }
        this.event_fk = event_fk;
    }

    /**
     * Indica si la tarea está completada.
     *
     * @return true si la tarea está completada, false en caso contrario
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Marca la tarea como completada o incompleta.
     *
     * @param completed true para marcar como completada, false para incompleta
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
