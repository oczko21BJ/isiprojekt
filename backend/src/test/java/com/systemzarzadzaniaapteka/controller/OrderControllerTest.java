package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.dto.OrderDto;
import com.systemzarzadzaniaapteka.model.AppUser;
import com.systemzarzadzaniaapteka.model.Order;
import com.systemzarzadzaniaapteka.model.OrderStatus;
import com.systemzarzadzaniaapteka.security.CustomOidcUser;
import com.systemzarzadzaniaapteka.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private CustomOidcUser createTestUser() {
        AppUser appUser = new AppUser();
        appUser.setId(1L);
        appUser.setName("Test User");
        appUser.setEmail("test@example.com");
        appUser.setRole("USER");
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "123");
        claims.put("name", "Test User");
        OidcIdToken idToken = new OidcIdToken("token", Instant.now(), Instant.now().plusSeconds(3600), claims);
        OidcUserInfo userInfo = new OidcUserInfo(claims);
        
        return new CustomOidcUser(
            appUser,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
            idToken,
            userInfo
        );
    }

    @Test
    void createOrder_Unauthenticated() {
        // When
        ResponseEntity<OrderDto> response = orderController.createOrder(new OrderDto(), null);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void createOrder_Authenticated() {
        // Given
        CustomOidcUser user = createTestUser();
        OrderDto orderDto = new OrderDto();
        Order order = new Order();
        when(orderService.createOrder(any(OrderDto.class))).thenReturn(order);

        // When
        ResponseEntity<OrderDto> response = orderController.createOrder(orderDto, user.getAppUser());

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getOrder() {
        // Given
        Long id = 1L;
        Order order = new Order();
        when(orderService.getOrderById(id)).thenReturn(order);

        // When
        ResponseEntity<OrderDto> response = orderController.getOrder(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void updateStatus() {
        // Given
        Long id = 1L;
        OrderStatus status = OrderStatus.COMPLETED;
        Order order = new Order();
        when(orderService.updateOrderStatus(id, status)).thenReturn(order);

        // When
        ResponseEntity<OrderDto> response = orderController.updateStatus(id, status);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getAllOrders() {
        // Given
        Order order1 = new Order();
        Order order2 = new Order();
        when(orderService.getAllOrders()).thenReturn(List.of(order1, order2));

        // When
        ResponseEntity<List<OrderDto>> response = orderController.getAllOrders();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
}