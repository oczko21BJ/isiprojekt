package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class CustomerRegistrationTest {

    @Test
    void testCustomerRegistrationCreationAndSetters() {
        // When
        CustomerRegistration registration = new CustomerRegistration();
        registration.setId(1L);

        Map<String, String> details = new HashMap<>();
        details.put("name", "Jan Kowalski");
        details.put("email", "jan@example.com");
        registration.setCustomerDetails(details);

        // Then
        assertEquals(1L, registration.getId());
        assertEquals(details, registration.getCustomerDetails());
    }

    @Test
    void testConstructorWithParameters() {
        // Given
        Map<String, String> details = new HashMap<>();
        details.put("name", "Anna Nowak");

        // When
        CustomerRegistration registration = new CustomerRegistration(details);

        // Then
        assertEquals(details, registration.getCustomerDetails());
    }

    @Test
    void testRegisterCustomer() {
        // Given
        CustomerRegistration registration = new CustomerRegistration();

        // When & Then
        assertDoesNotThrow(registration::registerCustomer);
    }
}