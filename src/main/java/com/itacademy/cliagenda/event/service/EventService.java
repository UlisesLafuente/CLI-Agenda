package com.itacademy.cliagenda.event.service;

import com.itacademy.cliagenda.common.exception.ValidationException;
import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.event.repository.EventRepository;

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

    private final EventRepository repo;

    public EventService(EventRepository repo) {
        this.repo = repo;
    }

    public Event createEvent(String title, String description,
                         LocalDateTime dateTime, boolean recurring,
                         boolean annualRecurring, int recurrenceInterval) {
        validateEvent(title, description, dateTime, recurring, annualRecurring, recurrenceInterval);
        int idEvent = generateNextId();
        Event newEvent = new Event(idEvent, title, description, dateTime, recurring, annualRecurring, recurrenceInterval);
        repo.save(newEvent);
        return newEvent;
    }

    public List<Event> getAllEvents() {
        return repo.findAll();
    }

    private int generateNextId() {
        List<Event> events = repo.findAll();
        int maxId = events.stream()
                .mapToInt(Event::getId)
                .max()
                .orElse(0);
        return maxId + 1;
    }

    public Event findEventById(int id) {
        return repo.findById(id);
    }

    public void deleteEventById(int id) {
        repo.delete(id);
    }

    public void updateEvent(Event event) {
        validateEvent(
                event.getTitle(),
                event.getDescription(),
                event.getDateTimeEvent(),
                event.isRecurring(),
                event.isAnnualRecurring(),
                event.getRecurrenceInterval()
        );
        repo.update(event);
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

    public String formatEventList(List<Event> events) {
        if (events == null || events.isEmpty()) {
            return "No events found";
        }
        StringBuilder sb = new StringBuilder();
        for (Event event : events) {
            sb.append("ID: ").append(event.getId())
                    .append(" | ").append(event.getTitle())
                    .append(" | ").append(event.getDateTimeEvent())
                    .append(" | ").append(event.isRecurring() ?
                            (event.isAnnualRecurring() ? "Recurring: yearly" :
                                    "Recurring: each " + event.getRecurrenceInterval() + " months") :
                            "Not recurring")
                    .append("\n");
        }
        return sb.toString();
    }

    public String formatEventDetail(Event event) {
        if (event == null) {
            return "Event not found";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(event.getId()).append("\n");
        sb.append("Title: ").append(event.getTitle()).append("\n");
        sb.append("Description: ").append(event.getDescription()).append("\n");
        sb.append("Date: ").append(event.getDateTimeEvent()).append("\n");
        sb.append("Recurring: ").append(event.isRecurring()).append("\n");
        if (event.isRecurring()) {
            if (event.isAnnualRecurring()) {
                sb.append("Recurrence: yearly\n");
            } else {
                sb.append("Recurrence: each ").append(event.getRecurrenceInterval()).append(" months\n");
            }
            sb.append("Next recurrencies:\n");
            for (LocalDateTime date : getNextRecurrencies(event)) {
                sb.append("  - ").append(date).append("\n");
            }
        }
        return sb.toString();
    }

    public boolean eventExists(int id) {
        try {
            repo.findById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void validateEvent(String title, String description, LocalDateTime dateTime,
                         boolean recurring, boolean annualRecurring, int recurrenceInterval) {
        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException("Event title cannot be empty");
        }
        if (dateTime == null) {
            throw new ValidationException("Event date cannot be null");
        }
        if (recurring && !annualRecurring && recurrenceInterval <= 0) {
            throw new ValidationException("Recurrence interval must be positive for recurring events");
        }
    }
}