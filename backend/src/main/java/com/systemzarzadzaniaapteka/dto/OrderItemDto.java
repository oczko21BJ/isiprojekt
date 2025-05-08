package com.systemzarzadzaniaapteka.dto;

import com.systemzarzadzaniaapteka.model.OrderItem;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

/**
 * Klasa DTO reprezentująca pozycję zamówienia.
 * Zawiera dane dotyczące konkretnego produktu w zamówieniu, takie jak identyfikator, nazwa,
 * ilość oraz cena, używane do transferu danych między warstwami aplikacji.
 */
public class OrderItemDto {
    private Long id;
    private Long medicineId;
    private String medicineName;
    private Integer quantity;
    private float price;
    private float subtotal;
    
    public OrderItemDto(OrderItem orderItem) {
    this.id = orderItem.getId();
    this.medicineId = orderItem.getMedicine() != null ? orderItem.getMedicine().getId() : null;
    this.medicineName = orderItem.getMedicine() != null ? orderItem.getMedicine().getName() : null;
    this.quantity = orderItem.getQuantity();

    this.price = orderItem.getMedicine() != null ? orderItem.getMedicine().getPrice() : 0f; // Uzywamy 0f, aby zachowac typ float
    this.subtotal = BigDecimal.valueOf(this.price).multiply(BigDecimal.valueOf(this.quantity)).floatValue();
}
    public OrderItemDto() {
        // pusty konstruktor potrzebny do deserializacji JSON
    }
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getMedicineId() {
        return medicineId;
    }
    
    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }
    
    public String getMedicineName() {
        return medicineName;
    }
    
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public float getPrice() {
        return price;
    }
    
    public void setPrice(float price) {
        this.price = price;
    }
    
    public float getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }
}
