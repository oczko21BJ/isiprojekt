package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca kartę klienta w systemie zarządzania apteką.
 * 
 * <p>Klasa CustomerCard przechowuje informacje o karcie klienta, w tym nazwisko klienta
 * oraz historię jego zamówień.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class CustomerCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    @OneToMany(mappedBy = "customerCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> history = new ArrayList<>();

    public CustomerCard() {
    }

    public CustomerCard(String customerName, List<Order> history) {
        this.customerName = customerName;
        this.history = history;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<Order> getHistory() {
        return history;
    }

    public void setHistory(List<Order> history) {
        this.history = history;
    }

    public void viewOrderHistory() {
        System.out.println("Order history for: " + customerName);
        if (history != null) {
            for (Order order : history) {
                System.out.println("Order ID: " + order.getId());
            }
        }
    }
}
