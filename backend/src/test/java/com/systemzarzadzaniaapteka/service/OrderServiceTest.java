package com.systemzarzadzaniaapteka.service;

import com.systemzarzadzaniaapteka.dto.OrderDto;
import com.systemzarzadzaniaapteka.dto.OrderItemDto;
import com.systemzarzadzaniaapteka.model.*;
import com.systemzarzadzaniaapteka.repository.MedicineRepository;
import com.systemzarzadzaniaapteka.repository.OrderRepository;
import com.systemzarzadzaniaapteka.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrder() {
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(1L);

        // Pozycja zamowienia: 2 sztuki po 50.0 = 100.0
        OrderItemDto itemDto = new OrderItemDto();
        itemDto.setMedicineId(1L);
        itemDto.setQuantity(2);
        itemDto.setPrice(50.0f);

        orderDto.setItems(Collections.singletonList(itemDto));

        // Ustaw prawidlowe wartosci
        orderDto.setSubtotal(100.0f);
        orderDto.setTax(10.0f);          // Przykladowo
        orderDto.setDeliveryFee(10.0f);  // Przykladowo
        orderDto.setTotal(120.0f);       // 100 + 10 + 10

        AppUser user = new AppUser();
        Medicine medicine = new Medicine();
        Order order = new Order();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(medicineRepository.findById(1L)).thenReturn(Optional.of(medicine));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.createOrder(orderDto);

        assertNotNull(result);
        verify(orderRepository).save(any(Order.class));
    }


    @Test
    void updateOrderStatus() {
        Long id = 1L;
        Order order = new Order();
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.updateOrderStatus(id, OrderStatus.COMPLETED);
        
        assertEquals(OrderStatus.COMPLETED, result.getStatus());
    }
}