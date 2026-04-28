package com.itacademy.cliagenda.note.model;

import com.itacademy.cliagenda.task.model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoteTest {

    @Test
    void shouldCreateNoteWithBasicFields() {
        Note note = new Note(1, "Buy milk", 0);

        assertEquals(1, note.getId());
        assertEquals("Buy milk", note.getBody());
        assertEquals(0, note.getTask_fk());
    }

    @Test
    void shouldCreateNoteWithTask() {
        Task task = new Task(5, "Task 1", 0);
        Note note = new Note(1, "Note", task);

        assertEquals(5, note.getTask_fk());
    }

    @Test
    void shouldChangeBody() {
        Note note = new Note(1, "Original", 0);
        note.changeBody("Updated");

        assertEquals("Updated", note.getBody());
    }

    @Test
    void shouldSetTaskFkWithInt() {
        Note note = new Note(1, "Note", 0);
        note.setTask_fk(10);

        assertEquals(10, note.getTask_fk());
    }
}