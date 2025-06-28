package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentIntentTest {

    @Test
    void shouldHoldIntentFields() {
        PaymentIntent pi = new PaymentIntent();
        pi.setClientSecret("abc123");
        pi.setStatus("requires_payment_method");

        assertEquals("abc123", pi.getClientSecret());
        assertEquals("requires_payment_method", pi.getStatus());
    }
}
