package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DrugComplaintTest {

    @Test
    void shouldSubmitComplaint() {
        Customer customer = new Customer("Anna", "anna@e.pl", "123", "Customer", "pass");
        DrugComplaint complaint = new DrugComplaint("Test", customer);

        assertEquals("Test", complaint.getDescription());
        assertEquals(customer, complaint.getReportedBy());
        assertDoesNotThrow(complaint::submitComplaint);
    }
}
