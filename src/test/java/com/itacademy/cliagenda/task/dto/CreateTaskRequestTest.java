package com.itacademy.cliagenda.task.dto;

import com.itacademy.cliagenda.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateTaskRequestTest {

    @Test
    void validRequest_withBodyAndEventId() {
        var request = new CreateTaskRequest("Test task", 1);
        assertEquals("Test task", request.body());
        assertEquals(1, request.eventId());
    }

    @Test
    void validRequest_withBodyOnly() {
        var request = new CreateTaskRequest("Test task", null);
        assertEquals("Test task", request.body());
        assertNull(request.eventId());
    }

    @Test
    void validRequest_withNullEventId() {
        var request = new CreateTaskRequest("Test task", null);
        assertNotNull(request.body());
    }

    @Test
    void invalidRequest_nullBody() {
        assertThrows(ValidationException.class, () -> new CreateTaskRequest(null, 1));
    }

    @Test
    void invalidRequest_emptyBody() {
        assertThrows(ValidationException.class, () -> new CreateTaskRequest("   ", 1));
    }

    @Test
    void invalidRequest_bodyTooLong() {
        String longBody = "a".repeat(251);
        assertThrows(ValidationException.class, () -> new CreateTaskRequest(longBody, 1));
    }

    @Test
    void invalidRequest_negativeEventId() {
        assertThrows(ValidationException.class, () -> new CreateTaskRequest("Test", -1));
    }

    @Test
    void validRequest_maxLengthBody() {
        String maxBody = "a".repeat(250);
        var request = new CreateTaskRequest(maxBody, null);
        assertEquals(250, request.body().length());
    }

    @Test
    void validRequest_eventIdZero() {
        var request = new CreateTaskRequest("Test", 0);
        assertEquals(0, request.eventId());
    }
}