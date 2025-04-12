package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerCardTest {

    @Test
    void shouldViewOrderHistory() {
        Order order = new Order();
        order.setId(123L);
        CustomerCard card = new CustomerCard();
        card.setCustomerName("Jan");
        card.setHistory(List.of(order));

        assertDoesNotThrow(card::viewOrderHistory);
    }
}
