package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryTest {

    @Test
    void shouldScheduleDelivery() {
        OnlineOrder order = new OnlineOrder();
        order.setId(42L);
        Delivery delivery = new Delivery(order, new Date());

        assertEquals(order, delivery.getOrder());
        assertDoesNotThrow(delivery::scheduleDelivery);
    }
}
