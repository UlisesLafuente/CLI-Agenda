package com.itacademy.cliagenda.common.formatter;

import com.itacademy.cliagenda.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public class EventFormatter {

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