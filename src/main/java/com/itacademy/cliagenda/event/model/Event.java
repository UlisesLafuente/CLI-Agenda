package com.itacademy.cliagenda.event.model;

import com.itacademy.cliagenda.common.exception.ValidationException;

import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa un evento en la agenda.
 * Un evento puede ser recurrente (mensual o anual) y tener una descripción.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class Event {

    private final int id;
    private String title;
    private String description;
    private LocalDateTime dateTimeEvent;
    private boolean recurring;
    private boolean annualRecurring;
    private int recurrenceInterval;

    /**
     * Constructor completo para crear un evento.
     *
     * @param id                 Identificador único del evento
     * @param title              Título del evento
     * @param description        Descripción del evento
     * @param dateTimeEvent      Fecha y hora del evento
     * @param recurring          Indica si el evento es recurrente
     * @param annualRecurring    Indica si la recurrencia es anual
     * @param recurrenceInterval Intervalo de recurrencia en meses (si recurring es true)
     */
    public Event(int id, String title, String description, LocalDateTime dateTimeEvent, boolean recurring, boolean annualRecurring, int recurrenceInterval) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateTimeEvent = dateTimeEvent;
        this.recurring = recurring;
        this.annualRecurring = annualRecurring;
        this.recurrenceInterval = recurrenceInterval;
    }

    /**
     * Obtiene el identificador del evento.
     *
     * @return Identificador del evento
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la descripción del evento.
     *
     * @return Descripción del evento
     */
    public String getDescription() {
        return description;
    }

    /**
     * Obtiene el título del evento.
     *
     * @return Título del evento
     */
    public String getTitle() {
        return title;
    }

    /**
     * Obtiene la fecha y hora del evento.
     *
     * @return Fecha y hora del evento
     */
    public LocalDateTime getDateTimeEvent() {
        return dateTimeEvent;
    }

    /**
     * Obtiene el intervalo de recurrencia en meses.
     *
     * @return Intervalo de recurrencia
     */
    public int getRecurrenceInterval() {
        return recurrenceInterval;
    }

    /**
     * Establece el intervalo de recurrencia en meses.
     *
     * @param recurrenceInterval Nuevo intervalo de recurrencia
     */
    public void setRecurrenceInterval(int recurrenceInterval) {
        this.recurrenceInterval = recurrenceInterval;
    }

    /**
     * Indica si el evento tiene recurrencia anual.
     *
     * @return true si es anual, false en caso contrario
     */
    public boolean isAnnualRecurring() {
        return annualRecurring;
    }

    /**
     * Establece si el evento tiene recurrencia anual.
     *
     * @param annualRecurring true para recurrencia anual
     */
    public void setAnnualRecurring(boolean annualRecurring) {
        this.annualRecurring = annualRecurring;
    }

    /**
     * Indica si el evento es recurrente.
     *
     * @return true si es recurrente, false en caso contrario
     */
    public boolean isRecurring() {
        return recurring;
    }

    /**
     * Establece si el evento es recurrente.
     *
     * @param recurring true para hacer el evento recurrente
     */
    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    /**
     * Modifica el título del evento.
     * Valida que el título no sea null y no exceda los 100 caracteres.
     *
     * @param title Nuevo título para el evento
     * @throws ValidationException si el título es null o excede 100 caracteres
     */
    public void changeTitle(String title) {
        if (title == null) {
            throw new ValidationException("Title cannot be null");
        }
        if (title.length() >= 100) {
            throw new ValidationException("Title must be shorter than 100 characters");
        }
        this.title = title;
    }

    /**
     * Modifica la descripción del evento.
     * Valida que la descripción no exceda los 500 caracteres.
     *
     * @param description Nueva descripción para el evento
     * @throws ValidationException si la descripción excede 500 caracteres
     */
    public void changeDescription(String description) {
        if (description != null && description.length() >= 500) {
            throw new ValidationException("Description must be shorter than 500 characters");
        }
        this.description = description;
    }

    /**
     * Modifica la fecha y hora del evento.
     *
     * @param datetime Nueva fecha y hora para el evento
     */
    public void changeDateEvent(LocalDateTime datetime) {
        this.dateTimeEvent = datetime;
    }
}
