package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class SalesReportTest {

    @Test
    void testSalesReportCreationAndSetters() {
        // Given
        LocalDate reportDate = LocalDate.now();
        Map<String, Double> salesMap = new HashMap<>();
        salesMap.put("Apap", 1500.0);

        // When
        SalesReport report = new SalesReport();
        report.setId(1L);
        report.setReportDate(reportDate);
        report.setStartDate(LocalDate.of(2023, 1, 1));
        report.setEndDate(LocalDate.of(2023, 1, 31));
        report.setTotalRevenue(4500.0);
        report.setTotalSales(120);
        report.setDetailsJson("{'Apap':1500}");
        report.setSalesByMedicine(salesMap);

        // Then
        assertEquals(1L, report.getId());
        assertEquals(reportDate, report.getReportDate());
        assertEquals(LocalDate.of(2023, 1, 1), report.getStartDate());
        assertEquals(LocalDate.of(2023, 1, 31), report.getEndDate());
        assertEquals(4500.0, report.getTotalRevenue());
        assertEquals(120, report.getTotalSales());
        assertEquals("{'Apap':1500}", report.getDetailsJson());
        assertEquals(salesMap, report.getSalesByMedicine());
    }

    @Test
    void testConstructorWithParameters() {
        // Given
        LocalDate reportDate = LocalDate.now();
        Map<String, Double> salesMap = new HashMap<>();
        salesMap.put("Ibuprom", 2300.0);

        // When
        SalesReport report = new SalesReport(reportDate, 6800.0, 150, salesMap);

        // Then
        assertEquals(reportDate, report.getReportDate());
        assertEquals(6800.0, report.getTotalRevenue());
        assertEquals(150, report.getTotalSales());
        assertEquals(salesMap, report.getSalesByMedicine());
    }

    @Test
    void testDisplayReport() {
        // Given
        Map<String, Double> salesMap = new HashMap<>();
        salesMap.put("Apap", 1500.0);
        SalesReport report = new SalesReport(LocalDate.now(), 4500.0, 120, salesMap);

        // When & Then
        assertDoesNotThrow(report::displayReport);
    }

    @Test
    void testGenerateReport() {
        // Given
        SalesReport report = new SalesReport();
        Map<String, String> filters = new HashMap<>();
        filters.put("month", "January");

        // When & Then
        assertDoesNotThrow(() -> report.generateReport(filters));
    }
}