package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class ComplaintTest {

    @Test
    void shouldInitializeWithDefaults() {
        Complaint c = new Complaint();
        assertEquals("OPEN", c.getStatus());
        assertNotNull(c.getCreatedAt());
    }
}