package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class StockCheckTest {

    @Test
    void testStockCheckCreationAndSetters() {
        // Given
        LocalDateTime checkDate = LocalDateTime.now();
        List<Medicine> medicines = new ArrayList<>();
        medicines.add(new Medicine());

        // When
        StockCheck stockCheck = new StockCheck();
        stockCheck.setId(1L);
        stockCheck.setCheckDate(checkDate);
        stockCheck.setCheckedMedicines(medicines);
        stockCheck.setCheckedBy("Jan Kowalski");

        // Then
        assertEquals(1L, stockCheck.getId());
        assertEquals(checkDate, stockCheck.getCheckDate());
        assertEquals(medicines, stockCheck.getCheckedMedicines());
        assertEquals("Jan Kowalski", stockCheck.getCheckedBy());
    }

    @Test
    void testConstructorWithParameters() {
        // Given
        LocalDateTime checkDate = LocalDateTime.now();
        List<Medicine> medicines = new ArrayList<>();
        medicines.add(new Medicine());

        // When
        StockCheck stockCheck = new StockCheck(checkDate, medicines, "Anna Nowak");

        // Then
        assertEquals(checkDate, stockCheck.getCheckDate());
        assertEquals(medicines, stockCheck.getCheckedMedicines());
        assertEquals("Anna Nowak", stockCheck.getCheckedBy());
    }

    @Test
    void testPerformCheck() {
        // Given
        List<Medicine> medicines = new ArrayList<>();
        Medicine medicine = new Medicine();
        medicine.setName("Apap");
        medicine.setQuantity(50);
        medicines.add(medicine);

        StockCheck stockCheck = new StockCheck(LocalDateTime.now(), medicines, "System");

        // When & Then
        assertDoesNotThrow(stockCheck::performCheck);
    }

    @Test
    void testSingletonInstance() {
        // When
        StockCheck instance1 = StockCheck.getInstance();
        StockCheck instance2 = StockCheck.getInstance();

        // Then
        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }
}