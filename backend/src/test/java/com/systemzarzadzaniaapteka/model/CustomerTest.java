package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    void shouldRegisterAndReportComplaint() {
        Customer customer = new Customer("Anna", "anna@example.com", "123", "Customer", "pass");
        assertDoesNotThrow(() -> {
            customer.register();
            customer.reportComplaint("Zgloszenie testowe");
        });
    }
}