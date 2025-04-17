package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    @Test
    void shouldCreatePayment() {
        Order order = new Order();
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(BigDecimal.valueOf(199.99f));

        assertEquals(order, payment.getOrder());
        assertEquals(BigDecimal.valueOf(199.99f), payment.getAmount());
    }
}
