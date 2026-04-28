package com.itacademy.cliagenda.event.dto;

import com.itacademy.cliagenda.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateEventRequestTest {

    @Test
    void validRequest_withTitleOnly() {
        var request = new UpdateEventRequest("New Title", null, null, null, null, null);
        assertEquals("New Title", request.title());
    }

    @Test
    void validRequest_withDescriptionOnly() {
        var request = new UpdateEventRequest(null, "New Description", null, null, null, null);
        assertEquals("New Description", request.description());
    }

    @Test
    void validRequest_withDateTimeOnly() {
        var dateTime = LocalDateTime.of(2026, 12, 25, 10, 0);
        var request = new UpdateEventRequest(null, null, dateTime, null, null, null);
        assertEquals(dateTime, request.dateTimeEvent());
    }

    @Test
    void validRequest_withRecurringOnly() {
        var request = new UpdateEventRequest(null, null, null, true, null, null);
        assertTrue(request.recurring());
    }

    @Test
    void validRequest_withAnnualRecurringOnly() {
        var request = new UpdateEventRequest(null, null, null, null, true, null);
        assertTrue(request.annualRecurring());
    }

    @Test
    void validRequest_withRecurrenceIntervalOnly() {
        var request = new UpdateEventRequest(null, null, null, null, null, 3);
        assertEquals(3, request.recurrenceInterval());
    }

    @Test
    void validRequest_allFields() {
        var dateTime = LocalDateTime.of(2026, 12, 25, 10, 0);
        var request = new UpdateEventRequest("Title", "Desc", dateTime, true, false, 3);
        assertEquals("Title", request.title());
        assertEquals("Desc", request.description());
        assertEquals(dateTime, request.dateTimeEvent());
        assertTrue(request.recurring());
        assertFalse(request.annualRecurring());
        assertEquals(3, request.recurrenceInterval());
    }

    @Test
    void invalidRequest_emptyTitle() {
        assertThrows(ValidationException.class, () -> new UpdateEventRequest("   ", null, null, null, null, null));
    }

    @Test
    void invalidRequest_titleTooLong() {
        String longTitle = "a".repeat(100);
        assertThrows(ValidationException.class, () -> new UpdateEventRequest(longTitle, null, null, null, null, null));
    }

    @Test
    void invalidRequest_descriptionTooLong() {
        String longDesc = "a".repeat(500);
        assertThrows(ValidationException.class, () -> new UpdateEventRequest(null, longDesc, null, null, null, null));
    }

    @Test
    void invalidRequest_negativeRecurrenceInterval() {
        assertThrows(ValidationException.class, () -> new UpdateEventRequest(null, null, null, null, null, -1));
    }

    @Test
    void invalidRequest_bothAnnualAndInterval() {
        assertThrows(ValidationException.class, () -> new UpdateEventRequest(null, null, null, true, true, 3));
    }

    @Test
    void invalidRequest_allFieldsNull() {
        assertThrows(ValidationException.class, () -> new UpdateEventRequest(null, null, null, null, null, null));
    }
}