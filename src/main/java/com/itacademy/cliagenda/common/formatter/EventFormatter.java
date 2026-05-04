package com.itacademy.cliagenda.common.formatter;

import com.itacademy.cliagenda.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Formateador para entidades de tipo Event.
 * Proporciona métodos para convertir eventos en representaciones de texto legibles,
 * incluyendo el cálculo de próximas recurrencias.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class EventFormatter {

    /**
     * Formatea una lista de eventos en una representación de texto legible.
     * Cada evento se muestra en una línea con su ID, título, fecha y tipo de recurrencia.
     *
     * @param events Lista de eventos a formatear
     * @return String con la representación formateada de la lista de eventos
     */
    public String formatList(List<Event> events) {
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

    /**
     * Formatea los detalles de un evento individual.
     * Muestra el ID, título, descripción, fecha, tipo de recurrencia y próximas ocurrencias.
     *
     * @param event Evento a formatear
     * @return String con los detalles formateados del evento
     */
    public String formatDetail(Event event) {
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

    /**
     * Calcula las próximas 5 ocurrencias de un evento recurrente.
     *
     * @param event Evento del cual calcular las recurrencias
     * @return Lista de fechas de las próximas recurrencias
     */
    private List<LocalDateTime> getNextRecurrencies(Event event) {
        java.util.ArrayList<LocalDateTime> dates = new java.util.ArrayList<>();
        if (!event.isRecurring()) return dates;

        int months = event.isAnnualRecurring() ? 12 : event.getRecurrenceInterval();
        LocalDateTime next = event.getDateTimeEvent();

        for (int i = 0; i < 5; i++) {
            next = next.plusMonths(months);
            dates.add(next);
        }
        return dates;
    }
}