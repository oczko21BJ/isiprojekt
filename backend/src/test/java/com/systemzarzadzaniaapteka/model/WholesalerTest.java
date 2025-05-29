package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WholesalerTest {

    @Test
    void testWholesalerCreationAndSetters() {
        // When
        Wholesaler wholesaler = new Wholesaler();
        wholesaler.setId(1L);
        wholesaler.setName("MediWholesale");
        wholesaler.setContactInfo("sales@mediwholesale.com");

        List<Medicine> medicines = new ArrayList<>();
        medicines.add(new Medicine());
        wholesaler.setSuppliedMedicines(medicines);

        // Then
        assertEquals(1L, wholesaler.getId());
        assertEquals("MediWholesale", wholesaler.getName());
        assertEquals("sales@mediwholesale.com", wholesaler.getContactInfo());
        assertEquals(medicines, wholesaler.getSuppliedMedicines());
    }

    @Test
    void testConstructorWithParameters() {
        // When
        Wholesaler wholesaler = new Wholesaler("PharmaDistributors", "info@pharmadist.com");

        // Then
        assertEquals("PharmaDistributors", wholesaler.getName());
        assertEquals("info@pharmadist.com", wholesaler.getContactInfo());
    }

    @Test
    void testSupplyMedicine() {
        // Given
        Wholesaler wholesaler = new Wholesaler();
        Medicine medicine = new Medicine();
        medicine.setName("Ibuprom");

        // When
        wholesaler.supplyMedicine(medicine);

        // Then
        assertTrue(wholesaler.getSuppliedMedicines().contains(medicine));
    }

    @Test
    void testProvideStock() {
        // Given
        Wholesaler wholesaler = new Wholesaler();
        Medicine medicine = new Medicine();
        medicine.setName("Apap");

        // When & Then
        assertDoesNotThrow(() -> wholesaler.provideStock(medicine, 100));
    }
}