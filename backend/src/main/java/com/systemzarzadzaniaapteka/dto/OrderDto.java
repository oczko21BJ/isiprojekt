package com.systemzarzadzaniaapteka.dto;

import com.systemzarzadzaniaapteka.model.Order;
import com.systemzarzadzaniaapteka.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Klasa DTO reprezentująca zamówienie.
 * Zawiera dane zamówienia, takie jak identyfikator, informacje o kliencie, pozycje zamówienia,
 * kwoty oraz status zamówienia, używane do transferu danych między warstwami aplikacji.
 */
public class OrderDto {
    private Long id;
    private Long customerId;
    private String customerName;
    private List<OrderItemDto> items;
    private double totalAmount;
    private float subtotal;          // suma pozycji bez podatku i dostawy
    private float tax;               // podatek
    private float deliveryFee;       // oplata za dostawe
    private float total;             // calkowita kwota do zaplaty (z frontu)
    private String deliveryAddress;  // adres dostawy
    private String specialInstructions; // specjalne uwagi
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderDto() {
    }

    public OrderDto(Order order) {
        this.id = order.getId();
        this.customerId = order.getUser() != null ? order.getUser().getId() : null;
        this.customerName = order.getUser() != null ? order.getUser().getName() : null;
        this.status = order.getStatus();
        this.totalAmount = order.getTotalAmount();
        this.createdAt = order.getOrderDate(); // przypisanie createdAt z orderDate
        this.updatedAt = null;

        if (order.getItems() != null) {
            this.items = order.getItems().stream()
                    .map(OrderItemDto::new) 
                    .collect(Collectors.toList());
        }
    }
    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }
    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(float deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
