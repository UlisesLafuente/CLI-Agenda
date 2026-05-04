package com.itacademy.cliagenda.event.service;

import com.itacademy.cliagenda.event.dto.CreateEventRequest;
import com.itacademy.cliagenda.event.dto.UpdateEventRequest;
import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.event.repository.IEventRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service para lógica de negocio de eventos.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class EventService {

    private final IEventRepository repo;

    public EventService(IEventRepository repo) {
        this.repo = repo;
    }

    public Event createEvent(String title, String description,
                             LocalDateTime dateTime, boolean recurring,
                             boolean annualRecurring, int recurrenceInterval) {
        CreateEventRequest request = new CreateEventRequest(title, description, dateTime, recurring, annualRecurring, recurrenceInterval);
        Event newEvent = new Event(0, request.title(), request.description(), request.dateTimeEvent(),
                request.recurring(), request.annualRecurring(), request.recurrenceInterval());
        int generatedId = repo.save(newEvent);
        return new Event(generatedId, request.title(), request.description(), request.dateTimeEvent(),
                request.recurring(), request.annualRecurring(), request.recurrenceInterval());
    }

    public List<Event> getAllEvents() {
        return repo.findAll();
    }

    public Event findEventById(int id) {
        return repo.findById(id);
    }

    public void deleteEventById(int id) {
        repo.delete(id);
    }

    public void updateEvent(Event event) {
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

    public boolean eventExists(int id) {
        try {
            repo.findById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}