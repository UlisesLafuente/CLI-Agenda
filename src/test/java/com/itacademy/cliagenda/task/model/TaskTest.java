package com.itacademy.cliagenda.task.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void shouldCreateTaskWithBasicFields() {
        Task task = new Task(1, "Buy groceries", null);

        assertEquals(1, task.getId());
        assertEquals("Buy groceries", task.getBody());
        assertNull(task.getEvent_fk());
        assertFalse(task.isCompleted());
    }

    @Test
    void shouldCreateTaskWithEventFk() {
        Task task = new Task(2, "Meeting", 5);

        assertEquals(5, task.getEvent_fk());
    }

    @Test
    void shouldCreateCompletedTask() {
        Task task = new Task(3, "Done task", null, true);

        assertTrue(task.isCompleted());
    }

    @Test
    void shouldChangeBody() {
        Task task = new Task(1, "Original", null);
        task.changeBody("Updated");

        assertEquals("Updated", task.getBody());
    }

    @Test
    void shouldSetCompleted() {
        Task task = new Task(1, "Task", null);

        task.setCompleted(true);
        assertTrue(task.isCompleted());

        task.setCompleted(false);
        assertFalse(task.isCompleted());
    }

    @Test
    void shouldSetEventFk() {
        Task task = new Task(1, "Task", null);

        task.setEvent_fk(10);
        assertEquals(10, task.getEvent_fk());
    }

    @Test
    void shouldSetEventFkToNull() {
        Task task = new Task(1, "Task", 5);
        task.setEvent_fk(null);

        assertNull(task.getEvent_fk());
    }
}