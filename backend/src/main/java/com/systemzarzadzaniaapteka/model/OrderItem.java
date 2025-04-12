package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Klasa reprezentująca pozycję zamówienia w systemie zarządzania apteką.
 * 
 * <p>Klasa OrderItem przechowuje informacje o pojedynczej pozycji w zamówieniu,
 * w tym ilość, cenę jednostkową oraz powiązanie z zamówieniem i konkretnym lekiem.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    private Integer quantity;
    private float price;

    public OrderItem() {}

    // Gettery i settery
     public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public Integer getQuantity() {
        return quantity;
    }

    // SETTERY
    public void setId(Long id) {
        this.id = id;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
     public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;  // Ustawia cene jednostkowa
    }
}
