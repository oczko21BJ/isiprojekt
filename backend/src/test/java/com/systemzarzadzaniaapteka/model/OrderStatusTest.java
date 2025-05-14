package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderStatusTest {

    @Test
    void shouldContainValues() {
        OrderStatus status = OrderStatus.PAID;
        assertEquals("PAID", status.name());
    }
}