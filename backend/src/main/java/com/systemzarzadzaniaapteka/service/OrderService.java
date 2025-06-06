package com.systemzarzadzaniaapteka.service;

import com.systemzarzadzaniaapteka.dto.OrderDto;
import com.systemzarzadzaniaapteka.dto.OrderItemDto;
import com.systemzarzadzaniaapteka.model.*;
import com.systemzarzadzaniaapteka.repository.OrderRepository;
import com.systemzarzadzaniaapteka.repository.MedicineRepository;
import com.systemzarzadzaniaapteka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.math.RoundingMode;

/**
 * Klasa serwisowa do zarządzania zamówieniami w systemie zarządzania apteką.
 * 
 * <p>Klasa OrderService zapewnia metody do obsługi zamówień,
 * w tym pobieranie wszystkich zamówień, pobieranie zamówień użytkownika,
 * pobieranie zamówienia po ID, tworzenie zamówienia z koszyka,
 * tworzenie zamówienia na podstawie danych DTO oraz aktualizację statusu zamówienia.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final MedicineRepository medicineRepository;
    private final UserRepository userRepository;
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Transactional
    public Order createOrderFromCart(Long userId) {
        Cart cart = cartService.getOrCreateCart(userId);

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cannot create order from empty cart");
        }
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        System.out.println("Updated order status: " + order.getStatus());

        float totalAmount = 0f;

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            Medicine medicine = cartItem.getMedicine();
            orderItem.setMedicine(medicine);
            orderItem.setQuantity(cartItem.getQuantity());
            BigDecimal price = BigDecimal.valueOf(cartItem.getUnitPrice());
            order.addItem(orderItem);

            BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalAmount += itemTotal.floatValue();
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(userId);

        return savedOrder;
    }
    @Transactional
    public Order createOrder(OrderDto orderDto) {
        Order order = new Order();

        // Pobieramy uzytkownika z bazy
        AppUser user = userRepository.findById(orderDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);

        // Ustawiamy daty
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        // Obliczamy subtotal z pozycji zamowienia jako BigDecimal
        BigDecimal calculatedSubtotal = BigDecimal.ZERO;

        for (OrderItemDto itemDto : orderDto.getItems()) {
            OrderItem orderItem = new OrderItem();
            Medicine medicine = medicineRepository.findById(itemDto.getMedicineId())
                    .orElseThrow(() -> new RuntimeException("Medicine not found"));

            orderItem.setMedicine(medicine);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPrice(itemDto.getPrice());

            order.addItem(orderItem);

            // Dodaj do subtotal z zaokragleniem do 2 miejsc
            BigDecimal itemTotal = BigDecimal.valueOf(itemDto.getPrice())
                    .multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            calculatedSubtotal = calculatedSubtotal.add(itemTotal);
        }

        // Zaokraglij subtotal do 2 miejsc po przecinku
        calculatedSubtotal = calculatedSubtotal.setScale(2, RoundingMode.HALF_UP);

        // Walidacja subtotalu
        BigDecimal expectedSubtotal = BigDecimal.valueOf(orderDto.getSubtotal()).setScale(2, RoundingMode.HALF_UP);
        if (calculatedSubtotal.subtract(expectedSubtotal).abs().compareTo(BigDecimal.valueOf(0.01)) > 0) {
            throw new RuntimeException("Subtotal mismatch");
        }

        // Obliczanie calkowitej kwoty jako BigDecimal i zaokraglenie
        BigDecimal totalAmount = calculatedSubtotal
                .add(BigDecimal.valueOf(orderDto.getTax()))
                .add(BigDecimal.valueOf(orderDto.getDeliveryFee()));

        totalAmount = totalAmount.setScale(2, RoundingMode.HALF_UP);

        // Walidacja calkowitej kwoty
        BigDecimal expectedTotal = BigDecimal.valueOf(orderDto.getTotal()).setScale(2, RoundingMode.HALF_UP);
        if (totalAmount.subtract(expectedTotal).abs().compareTo(BigDecimal.valueOf(0.01)) > 0) {
            System.out.println("Calculated total: " + totalAmount);
            System.out.println("Expected total: " + expectedTotal);
            throw new RuntimeException("Total amount mismatch");
        }

        // Ustawienie koncowej kwoty jako float
        order.setTotalAmount(totalAmount.doubleValue());
        // Ustawianie danych dostawy i instrukcji
        order.setDeliveryAddress(orderDto.getDeliveryAddress());
        order.setSpecialInstructions(orderDto.getSpecialInstructions());

        // Zapisanie zamowienia
        Order savedOrder = orderRepository.save(order);
        System.out.println("Savedorderr: " + savedOrder);

        return savedOrder;
    }

    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
