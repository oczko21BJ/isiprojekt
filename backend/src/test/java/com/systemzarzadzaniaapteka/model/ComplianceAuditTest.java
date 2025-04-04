package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComplianceAuditTest {

    @Test
    void shouldPerformAudit() {
        Manager manager = new Manager();
        manager.setName("Kierownik");
        ComplianceAudit audit = new ComplianceAudit("Sprawdzenie", manager);

        assertEquals("Sprawdzenie", audit.getAuditDetails());
        assertDoesNotThrow(audit::performAudit);
    }
}
