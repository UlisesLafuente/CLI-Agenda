package com.itacademy.cliagenda.event.service;

import com.itacademy.cliagenda.common.validator.EventValidator;
import com.itacademy.cliagenda.event.dto.CreateEventRequest;
import com.itacademy.cliagenda.event.dto.UpdateEventRequest;
import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.event.repository.IEventRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service para lógica de negocio de eventos.
 * Gestiona las operaciones CRUD de eventos y calcula recurrencias.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class EventService {

    private final IEventRepository repo;
    private final EventValidator validator;

    /**
     * Constructor del servicio de eventos.
     *
     * @param repo Repositorio de eventos
     */
    public EventService(IEventRepository repo) {
        this.repo = repo;
        this.validator = new EventValidator();
    }

    /**
     * Crea un nuevo evento en la agenda.
     *
     * @param title              Título del evento
     * @param description        Descripción del evento
     * @param dateTime           Fecha y hora del evento
     * @param recurring          Indica si el evento es recurrente
     * @param annualRecurring    Indica si la recurrencia es anual
     * @param recurrenceInterval Intervalo de recurrencia en meses
     * @return El evento creado con su ID generado
     * @throws ValidationException si el título o descripción no son válidos
     */
    public Event createEvent(String title, String description,
                             LocalDateTime dateTime, boolean recurring,
                             boolean annualRecurring, int recurrenceInterval) {
        validator.validateTitle(title);
        validator.validateDescription(description);
        CreateEventRequest request = new CreateEventRequest(title, description, dateTime, recurring, annualRecurring, recurrenceInterval);
        Event newEvent = new Event(0, request.title(), request.description(), request.dateTimeEvent(),
                request.recurring(), request.annualRecurring(), request.recurrenceInterval());
        int generatedId = repo.save(newEvent);
        return new Event(generatedId, request.title(), request.description(), request.dateTimeEvent(),
                request.recurring(), request.annualRecurring(), request.recurrenceInterval());
    }

    /**
     * Obtiene todos los eventos de la agenda.
     *
     * @return Lista de todos los eventos
     */
    public List<Event> getAllEvents() {
        return repo.findAll();
    }

    /**
     * Busca un evento por su identificador.
     *
     * @param id Identificador del evento
     * @return El evento encontrado
     * @throws EntityNotFoundException si no se encuentra el evento
     */
    public Event findEventById(int id) {
        return repo.findById(id);
    }

    /**
     * Elimina un evento de la agenda.
     *
     * @param id Identificador del evento a eliminar
     */
    public void deleteEventById(int id) {
        repo.delete(id);
    }

    /**
     * Actualiza un evento existente.
     *
     * @param event Evento con los nuevos datos
     * @throws ValidationException si el título o descripción no son válidos
     */
    public void updateEvent(Event event) {
        validator.validateTitle(event.getTitle());
        validator.validateDescription(event.getDescription());
        UpdateEventRequest request = new UpdateEventRequest(
                event.getTitle(),
                event.getDescription(),
                event.getDateTimeEvent(),
                event.isRecurring(),
                event.isAnnualRecurring(),
                event.getRecurrenceInterval()
        );
        Event existingEvent = repo.findById(event.getId());

        if (request.title() != null && !request.title().isEmpty()) {
            existingEvent.changeTitle(request.title());
        }
        if (request.description() != null) {
            existingEvent.changeDescription(request.description());
        }
        if (request.dateTimeEvent() != null) {
            existingEvent.changeDateEvent(request.dateTimeEvent());
        }
        if (request.recurring() != null) {
            existingEvent.setRecurring(request.recurring());
        }
        if (request.annualRecurring() != null) {
            existingEvent.setAnnualRecurring(request.annualRecurring());
        }
        if (request.recurrenceInterval() != null) {
            existingEvent.setRecurrenceInterval(request.recurrenceInterval());
        }

        repo.update(existingEvent);
    }

    /**
     * Calcula las próximas 5 ocurrencias de un evento recurrente.
     *
     * @param event Evento del cual calcular las recurrencias
     * @return Lista de fechas de las próximas recurrencias
     */
    public List<LocalDateTime> getNextRecurrencies(Event event) {
        List<LocalDateTime> dates = new ArrayList<>();
        if (!event.isRecurring()) return dates;

        int months = event.isAnnualRecurring() ? 12 : event.getRecurrenceInterval();
        LocalDateTime next = event.getDateTimeEvent();

        for (int i = 0; i < 5; i++) {
            next = next.plusMonths(months);
            dates.add(next);
        }
        return dates;
    }

    /**
     * Verifica si existe un evento con el identificador dado.
     *
     * @param id Identificador a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean eventExists(int id) {
        try {
            repo.findById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}