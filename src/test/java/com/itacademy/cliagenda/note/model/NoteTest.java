package com.itacademy.cliagenda.note.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NoteTest {

    @Test
    void shouldCreateNoteWithBasicFields() {
        Note note = new Note(1, "Buy milk", null);

        assertEquals(1, note.getId());
        assertEquals("Buy milk", note.getBody());
        assertNull(note.getTask_fk());
    }

    @Test
    void shouldCreateNoteWithTaskId() {
        Note note = new Note(1, "Note", 5);

        assertEquals(5, note.getTask_fk());
    }

    @Test
    void shouldChangeBody() {
        Note note = new Note(1, "Original", null);
        note.changeBody("Updated");

        assertEquals("Updated", note.getBody());
    }

    @Test
    void shouldSetTaskFkWithInteger() {
        Note note = new Note(1, "Note", null);
        note.setTask_fk(10);

        assertEquals(10, note.getTask_fk());
    }

    @Test
    void shouldSetTaskFkToNull() {
        Note note = new Note(1, "Note", 5);
        note.setTask_fk(null);

        assertNull(note.getTask_fk());
    }
}