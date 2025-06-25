package com.systemzarzadzaniaapteka.dto;

import com.systemzarzadzaniaapteka.model.OrderStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDtoTest {

    @Test
    void shouldHoldOrderData() {
        OrderDto dto = new OrderDto();
        dto.setCustomerId(1L);
        dto.setCustomerName("Anna");
        dto.setStatus(OrderStatus.PENDING);
        dto.setCreatedAt(LocalDateTime.now());

        assertEquals(1L, dto.getCustomerId());
        assertEquals("Anna", dto.getCustomerName());
        assertEquals(OrderStatus.PENDING, dto.getStatus());
        assertNotNull(dto.getCreatedAt());
    }
}
