package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class SalesDataTest {

    @Test
    void testSalesDataCreationAndSetters() {
        // Given
        LocalDate salesDate = LocalDate.now();
        Map<String, Double> salesMap = new HashMap<>();
        salesMap.put("Apap", 1500.0);
        salesMap.put("Ibuprom", 2300.0);

        // When
        SalesData salesData = new SalesData();
        salesData.setId(1L);
        salesData.setSalesDate(salesDate);
        salesData.setTotalRevenue(4500.0);
        salesData.setTotalItemsSold(120);
        salesData.setDetailsJson("{'Apap':1500,'Ibuprom':2300}");
        salesData.setSalesByMedicine(salesMap);

        // Then
        assertEquals(1L, salesData.getId());
        assertEquals(salesDate, salesData.getSalesDate());
        assertEquals(4500.0, salesData.getTotalRevenue());
        assertEquals(120, salesData.getTotalItemsSold());
        assertEquals("{'Apap':1500,'Ibuprom':2300}", salesData.getDetailsJson());
        assertEquals(salesMap, salesData.getSalesByMedicine());
    }

    @Test
    void testDisplaySalesSummary() {
        // Given
        Map<String, Double> salesMap = new HashMap<>();
        salesMap.put("Apap", 1500.0);
        SalesData salesData = new SalesData(LocalDate.now(), 4500.0, 120, salesMap);

        // When & Then
        assertDoesNotThrow(salesData::displaySalesSummary);
    }

    @Test
    void testAnalyzeSales() {
        // Given
        SalesData salesData = new SalesData();

        // When
        SalesReport report = salesData.analyzeSales();

        // Then
        assertNotNull(report);
    }
}