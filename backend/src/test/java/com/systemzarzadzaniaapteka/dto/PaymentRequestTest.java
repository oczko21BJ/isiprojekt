package com.systemzarzadzaniaapteka.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentRequestTest {

    @Test
    void shouldSetAndGetAmount() {
        PaymentRequest pr = new PaymentRequest();
        pr.setAmount(99.99F);
        assertEquals(99.99F, pr.getAmount());
    }
}
