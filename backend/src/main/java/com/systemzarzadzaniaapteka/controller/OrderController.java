package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.model.Order;
import com.systemzarzadzaniaapteka.dto.OrderDto;
import com.systemzarzadzaniaapteka.dto.OrderItemDto;
import com.systemzarzadzaniaapteka.model.OrderStatus;
import com.systemzarzadzaniaapteka.service.OrderService;
import com.systemzarzadzaniaapteka.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.systemzarzadzaniaapteka.security.CustomOidcUser;

import java.util.List;

/**
 * Klasa kontrolera do zarządzania zamówieniami w systemie zarządzania apteką.
 * 
 * <p>Klasa OrderController zapewnia endpointy do obsługi zamówień,
 * w tym tworzenie nowych zamówień, pobieranie zamówienia po ID,
 * aktualizowanie statusu zamówienia oraz pobieranie wszystkich zamówień.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    //@PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto,
                                                @AuthenticationPrincipal AppUser user) {
        System.out.println("====> [createOrder] Wywolanie metody POST /api/orders");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        System.out.println("🆔 AppUser ID: " + user.getId());
        System.out.println("🧑 Imie i nazwisko: " + user.getName());
        System.out.println("🎭 Role: " + user.getAuthorities());
       // OrderDto orderDto = new OrderDto();
        //System.out.println("Authenticated user roles: " + user.getAuthorities());
       // AppUser user = user.getAppUser();
        orderDto.setCustomerId(user.getId());
        orderDto.setCustomerName(user.getName());
      //  orderDto.setItems(items);
        orderDto.setStatus(OrderStatus.PENDING); // lub domyslnie ustawiane w serwisie
        System.out.println("🎭 sttutspending: " + OrderStatus.PENDING);

        orderDto.setCreatedAt(java.time.LocalDateTime.now());
        System.out.println("🎭 localdatatimeczas: " + java.time.LocalDateTime.now());


        Order order = orderService.createOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(order));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(convertToDto(order));
    }
    @GetMapping("/me")
    public ResponseEntity<String> whoAmI(@AuthenticationPrincipal CustomOidcUser customUser) {
        if (customUser  == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }
        return ResponseEntity.ok("Zalogowany jako: " + customUser.getAppUser().getName());
    }
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<OrderDto> updateStatus(@PathVariable Long id,
                                                 @RequestBody OrderStatus status) {
        Order order = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(convertToDto(order));
    }
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderDto> orderDtos = orders.stream()
                .map(OrderDto::new)
                .toList();
        return ResponseEntity.ok(orderDtos);
    }
    // Metoda pomocnicza do konwersji na DTO - zaimplementuj zgodnie z Twoim DTO
    private OrderDto convertToDto(Order order) {
        // konwersja Order do OrderDto
        return new OrderDto(order);
    }
}
