package com.itacademy.cliagenda.event.model;

import com.itacademy.cliagenda.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void shouldCreateEventWithAllFields() {
        LocalDateTime date = LocalDateTime.of(2026, 6, 15, 14, 0);
        Event event = new Event(1, "Meeting", "Team meeting", date, true, false, 2);

        assertEquals(1, event.getId());
        assertEquals("Meeting", event.getTitle());
        assertEquals("Team meeting", event.getDescription());
        assertEquals(date, event.getDateTimeEvent());
        assertTrue(event.isRecurring());
        assertFalse(event.isAnnualRecurring());
        assertEquals(2, event.getRecurrenceInterval());
    }

    @Test
    void shouldCreateNonRecurringEvent() {
        LocalDateTime date = LocalDateTime.of(2026, 6, 15, 14, 0);
        Event event = new Event(1, "One-time", "Description", date, false, false, 0);

        assertFalse(event.isRecurring());
    }

    @Test
    void shouldCreateAnnualRecurringEvent() {
        LocalDateTime date = LocalDateTime.of(2026, 6, 15, 14, 0);
        Event event = new Event(1, "Birthday", "Annual", date, true, true, 0);

        assertTrue(event.isRecurring());
        assertTrue(event.isAnnualRecurring());
    }

    @Test
    void shouldChangeTitle() {
        Event event = new Event(1, "Old", "Desc", LocalDateTime.now(), false, false, 0);
        event.changeTitle("New Title");

        assertEquals("New Title", event.getTitle());
    }

    @Test
    void shouldChangeDescription() {
        Event event = new Event(1, "Title", "Old", LocalDateTime.now(), false, false, 0);
        event.changeDescription("New description");

        assertEquals("New description", event.getDescription());
    }

    @Test
    void shouldChangeDateEvent() {
        LocalDateTime oldDate = LocalDateTime.of(2026, 1, 1, 10, 0);
        LocalDateTime newDate = LocalDateTime.of(2026, 12, 25, 18, 0);
        Event event = new Event(1, "Title", "Desc", oldDate, false, false, 0);

        event.changeDateEvent(newDate);

        assertEquals(newDate, event.getDateTimeEvent());
    }

    @Test
    void shouldUpdateRecurringStatus() {
        Event event = new Event(1, "Title", "Desc", LocalDateTime.now(), false, false, 0);

        event.setRecurring(true);
        assertTrue(event.isRecurring());

        event.setRecurring(false);
        assertFalse(event.isRecurring());
    }

    @Test
    void shouldThrowExceptionWhenTitleIsNull() {
        Event event = new Event(1, "Title", "Desc", LocalDateTime.now(), false, false, 0);

        assertThrows(ValidationException.class, () -> event.changeTitle(null));
    }

    @Test
    void shouldThrowExceptionWhenTitleTooLong() {
        Event event = new Event(1, "Title", "Desc", LocalDateTime.now(), false, false, 0);

        String longTitle = "a".repeat(100);
        assertThrows(ValidationException.class, () -> event.changeTitle(longTitle));
    }

    @Test
    void shouldThrowExceptionWhenDescriptionTooLong() {
        Event event = new Event(1, "Title", "Desc", LocalDateTime.now(), false, false, 0);

        String longDesc = "a".repeat(500);
        assertThrows(ValidationException.class, () -> event.changeDescription(longDesc));
    }
}