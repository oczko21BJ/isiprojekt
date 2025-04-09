package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.*;

/**
 * Klasa reprezentująca pojedynczy przedmiot w koszyku zakupowym w systemie zarządzania apteką.
 * 
 * <p>Klasa CartItem przechowuje informacje o konkretnym leku dodanym do koszyka,
 * w tym ilość oraz powiązanie z koszykiem i lekiem.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Medicine medicine;

    private Integer quantity;

    public CartItem() {}

    // Gettery i settery
    public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public Cart getCart() {
    return cart;
}

public void setCart(Cart cart) {
    this.cart = cart;
}
    @Transient
public Medicine getMedicine() {
    return medicine;
}
@Transient
public void setMedicine(Medicine medicine) {
    this.medicine = medicine;
}

public Integer getQuantity() {
    return quantity;
}

public void setQuantity(Integer quantity) {
    this.quantity = quantity;
}

  public Long getProductId() {
        return medicine != null ? medicine.getId() : null; // Zwrocenie ID leku
    }

    public float getUnitPrice() {
        return medicine != null ? medicine.getPrice() : 0f; // Zwrocenie ceny leku jako float
    }
}
