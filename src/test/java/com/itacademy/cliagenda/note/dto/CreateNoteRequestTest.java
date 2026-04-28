package com.itacademy.cliagenda.note.dto;

import com.itacademy.cliagenda.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateNoteRequestTest {

    @Test
    void validRequest_withBodyAndTaskId() {
        var request = new CreateNoteRequest("Test note", 1);
        assertEquals("Test note", request.body());
        assertEquals(1, request.taskId());
    }

    @Test
    void invalidRequest_nullBody() {
        assertThrows(ValidationException.class, () -> new CreateNoteRequest(null, 1));
    }

    @Test
    void invalidRequest_emptyBody() {
        assertThrows(ValidationException.class, () -> new CreateNoteRequest("   ", 1));
    }

    @Test
    void invalidRequest_bodyTooLong() {
        String longBody = "a".repeat(251);
        assertThrows(ValidationException.class, () -> new CreateNoteRequest(longBody, 1));
    }

    @Test
    void invalidRequest_nullTaskId() {
        assertThrows(ValidationException.class, () -> new CreateNoteRequest("Test", null));
    }

    @Test
    void invalidRequest_zeroTaskId() {
        assertThrows(ValidationException.class, () -> new CreateNoteRequest("Test", 0));
    }

    @Test
    void invalidRequest_negativeTaskId() {
        assertThrows(ValidationException.class, () -> new CreateNoteRequest("Test", -1));
    }

    @Test
    void validRequest_maxLengthBody() {
        String maxBody = "a".repeat(250);
        var request = new CreateNoteRequest(maxBody, 1);
        assertEquals(250, request.body().length());
    }
}