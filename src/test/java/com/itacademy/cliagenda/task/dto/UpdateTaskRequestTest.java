package com.itacademy.cliagenda.task.dto;

import com.itacademy.cliagenda.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateTaskRequestTest {

    @Test
    void validRequest_withBodyOnly() {
        var request = new UpdateTaskRequest("Updated body", null, null);
        assertEquals("Updated body", request.body());
    }

    @Test
    void validRequest_withEventIdOnly() {
        var request = new UpdateTaskRequest(null, 1, null);
        assertEquals(1, request.eventId());
    }

    @Test
    void validRequest_withCompletedOnly() {
        var request = new UpdateTaskRequest(null, null, true);
        assertTrue(request.completed());
    }

    @Test
    void validRequest_withAllFields() {
        var request = new UpdateTaskRequest("New body", 1, true);
        assertEquals("New body", request.body());
        assertEquals(1, request.eventId());
        assertTrue(request.completed());
    }

    @Test
    void invalidRequest_emptyBody() {
        assertThrows(ValidationException.class, () -> new UpdateTaskRequest("   ", null, null));
    }

    @Test
    void invalidRequest_bodyTooLong() {
        String longBody = "a".repeat(251);
        assertThrows(ValidationException.class, () -> new UpdateTaskRequest(longBody, null, null));
    }

    @Test
    void invalidRequest_negativeEventId() {
        assertThrows(ValidationException.class, () -> new UpdateTaskRequest(null, -1, null));
    }

    @Test
    void invalidRequest_allFieldsNull() {
        assertThrows(ValidationException.class, () -> new UpdateTaskRequest(null, null, null));
    }

    @Test
    void validRequest_negativeCompletedIsIgnored() {
        var request = new UpdateTaskRequest(null, null, false);
        assertFalse(request.completed());
    }
}