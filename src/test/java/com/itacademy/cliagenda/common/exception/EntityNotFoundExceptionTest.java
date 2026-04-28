package com.itacademy.cliagenda.common.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithCorrectMessage() {
        EntityNotFoundException ex = new EntityNotFoundException("Task", 1);

        assertEquals("Task with id 1 not found", ex.getMessage());
        assertEquals("Task", ex.getEntityName());
        assertEquals(1, ex.getEntityId());
    }

    @Test
    void shouldStoreEntityNameAndId() {
        EntityNotFoundException ex = new EntityNotFoundException("Note", 5);

        assertEquals("Note", ex.getEntityName());
        assertEquals(5, ex.getEntityId());
    }
}