package com.itacademy.cliagenda.event.dto;

import com.itacademy.cliagenda.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CreateEventRequestTest {

    @Test
    void validRequest_allFields() {
        var dateTime = LocalDateTime.of(2026, 12, 25, 10, 0);
        var request = new CreateEventRequest("Title", "Description", dateTime, true, false, 3);
        assertEquals("Title", request.title());
        assertEquals("Description", request.description());
        assertEquals(dateTime, request.dateTimeEvent());
        assertTrue(request.recurring());
        assertFalse(request.annualRecurring());
        assertEquals(3, request.recurrenceInterval());
    }

    @Test
    void validRequest_minimalFields() {
        var dateTime = LocalDateTime.of(2026, 12, 25, 10, 0);
        var request = new CreateEventRequest("Title", null, dateTime, false, false, 0);
        assertEquals("Title", request.title());
        assertEquals(dateTime, request.dateTimeEvent());
    }

    @Test
    void invalidRequest_nullTitle() {
        var dateTime = LocalDateTime.of(2026, 12, 25, 10, 0);
        assertThrows(ValidationException.class, () -> new CreateEventRequest(null, "Desc", dateTime, false, false, 0));
    }

    @Test
    void invalidRequest_emptyTitle() {
        var dateTime = LocalDateTime.of(2026, 12, 25, 10, 0);
        assertThrows(ValidationException.class, () -> new CreateEventRequest("   ", "Desc", dateTime, false, false, 0));
    }

    @Test
    void invalidRequest_titleTooLong() {
        String longTitle = "a".repeat(100);
        var dateTime = LocalDateTime.of(2026, 12, 25, 10, 0);
        assertThrows(ValidationException.class, () -> new CreateEventRequest(longTitle, "Desc", dateTime, false, false, 0));
    }

    @Test
    void invalidRequest_descriptionTooLong() {
        String longDesc = "a".repeat(500);
        var dateTime = LocalDateTime.of(2026, 12, 25, 10, 0);
        assertThrows(ValidationException.class, () -> new CreateEventRequest("Title", longDesc, dateTime, false, false, 0));
    }

    @Test
    void invalidRequest_nullDateTime() {
        assertThrows(ValidationException.class, () -> new CreateEventRequest("Title", null, null, false, false, 0));
    }

    @Test
    void invalidRequest_negativeRecurrenceInterval() {
        var dateTime = LocalDateTime.of(2026, 12, 25, 10, 0);
        assertThrows(ValidationException.class, () -> new CreateEventRequest("Title", null, dateTime, true, false, -1));
    }

    @Test
    void invalidRequest_bothAnnualAndInterval() {
        var dateTime = LocalDateTime.of(2026, 12, 25, 10, 0);
        assertThrows(ValidationException.class, () -> new CreateEventRequest("Title", null, dateTime, true, true, 3));
    }

    @Test
    void validRequest_annualRecurring() {
        var dateTime = LocalDateTime.of(2026, 12, 25, 10, 0);
        var request = new CreateEventRequest("Title", null, dateTime, true, true, 0);
        assertTrue(request.annualRecurring());
        assertEquals(0, request.recurrenceInterval());
    }
}