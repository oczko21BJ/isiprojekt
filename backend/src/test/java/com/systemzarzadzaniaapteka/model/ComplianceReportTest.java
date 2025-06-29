package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComplianceReportTest {

    @Test
    void shouldSubmitReport() {
        Pharmacist pharmacist = new Pharmacist();
        pharmacist.setName("Farmaceuta");
        ComplianceReport report = new ComplianceReport("Brak etykiety", pharmacist);

        assertEquals("Brak etykiety", report.getIssue());
        assertEquals(pharmacist, report.getReportedBy());
        assertDoesNotThrow(report::submitReport);
    }
}
