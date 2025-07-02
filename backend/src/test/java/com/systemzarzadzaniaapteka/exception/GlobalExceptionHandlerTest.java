package com.systemzarzadzaniaapteka.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleProductNotFound() {
        ProductNotFoundException ex = new ProductNotFoundException("Not found");
        ResponseEntity<?> response = handler.handleProductNotFound(ex);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void shouldHandleGenericException() {
        Exception ex = new RuntimeException("Unknown");
        ResponseEntity<?> response = handler.handleGenericException(ex);
        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Wystapil blad"));
    }

    @Test
    void shouldHandlePaymentException() {
        PaymentProcessingException ex = new PaymentProcessingException("Blad platnosci");
        ResponseEntity<String> response = handler.handlePaymentException(ex);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Blad platnosci", response.getBody());
    }
}
