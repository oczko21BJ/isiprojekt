package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SupplierTest {

    @Test
    void testSupplierCreationAndSetters() {
        // When
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Pharma Inc.");
        supplier.setContactInfo("contact@pharma.com");

        List<Medicine> medicines = new ArrayList<>();
        medicines.add(new Medicine());
        supplier.setSuppliedMedicines(medicines);

        // Then
        assertEquals(1L, supplier.getId());
        assertEquals("Pharma Inc.", supplier.getName());
        assertEquals("contact@pharma.com", supplier.getContactInfo());
        assertEquals(medicines, supplier.getSuppliedMedicines());
    }

    @Test
    void testConstructorWithParameters() {
        // When
        Supplier supplier = new Supplier("MediCorp", "info@medicorp.com");

        // Then
        assertEquals("MediCorp", supplier.getName());
        assertEquals("info@medicorp.com", supplier.getContactInfo());
    }

    @Test
    void testSupplyMedicine() {
        // Given
        Supplier supplier = new Supplier();
        Medicine medicine = new Medicine();
        medicine.setName("Apap");

        // When
        supplier.supplyMedicine(medicine);

        // Then
        assertTrue(supplier.getSuppliedMedicines().contains(medicine));
        assertEquals(supplier, medicine.getSupplier());
    }

    @Test
    void testProvideMedicine() {
        // Given
        Supplier supplier = new Supplier("Pharma Inc.", "contact@pharma.com");

        // When & Then
        assertDoesNotThrow(supplier::provideMedicine);
    }
}