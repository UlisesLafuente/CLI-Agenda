package com.itacademy.cliagenda.note.dto;

import com.itacademy.cliagenda.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateNoteRequestTest {

    @Test
    void validRequest_withBodyOnly() {
        var request = new UpdateNoteRequest("Updated note", null);
        assertEquals("Updated note", request.body());
    }

    @Test
    void validRequest_withTaskIdOnly() {
        var request = new UpdateNoteRequest(null, 2);
        assertEquals(2, request.taskId());
    }

    @Test
    void validRequest_withBothFields() {
        var request = new UpdateNoteRequest("New note", 1);
        assertEquals("New note", request.body());
        assertEquals(1, request.taskId());
    }

    @Test
    void invalidRequest_emptyBody() {
        assertThrows(ValidationException.class, () -> new UpdateNoteRequest("   ", null));
    }

    @Test
    void invalidRequest_bodyTooLong() {
        String longBody = "a".repeat(251);
        assertThrows(ValidationException.class, () -> new UpdateNoteRequest(longBody, null));
    }

    @Test
    void invalidRequest_negativeTaskId() {
        assertThrows(ValidationException.class, () -> new UpdateNoteRequest(null, -1));
    }

    @Test
    void invalidRequest_allFieldsNull() {
        assertThrows(ValidationException.class, () -> new UpdateNoteRequest(null, null));
    }
}