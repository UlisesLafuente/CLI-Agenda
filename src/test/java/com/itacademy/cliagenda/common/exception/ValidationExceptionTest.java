package com.itacademy.cliagenda.common.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        ValidationException ex = new ValidationException("Field cannot be empty");
        
        assertEquals("Field cannot be empty", ex.getMessage());
    }

    @Test
    void shouldCreateExceptionWithMessageAndCause() {
        RuntimeException cause = new RuntimeException("Original error");
        ValidationException ex = new ValidationException("Validation failed", cause);
        
        assertEquals("Validation failed", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}