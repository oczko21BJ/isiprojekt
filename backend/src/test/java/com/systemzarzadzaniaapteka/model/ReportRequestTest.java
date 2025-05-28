package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReportRequestTest {

    @Test
    void testReportRequestCreationAndSetters() {
        // When
        ReportRequest request = new ReportRequest();
        request.setType("Sprzedaz");
        request.setParameters("month=January");

        // Then
        assertEquals("Sprzedaz", request.getType());
        assertEquals("month=January", request.getParameters());
    }

    @Test
    void testConstructorWithParameters() {
        // When
        ReportRequest request = new ReportRequest("Magazyn", "category=Antybiotyki");

        // Then
        assertEquals("Magazyn", request.getType());
        assertEquals("category=Antybiotyki", request.getParameters());
    }
}