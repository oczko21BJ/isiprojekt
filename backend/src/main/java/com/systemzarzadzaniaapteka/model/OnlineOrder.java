package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca zamówienie online w systemie zarządzania apteką.
 * 
 * <p>Klasa OnlineOrder przechowuje informacje o zamówieniu online złożonym przez klienta,
 * w tym listę zamówionych leków oraz powiązanie z konkretnym klientem.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class OnlineOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacja z Medicine (wiele lekow w jednym zamowieniu)
    @ManyToMany
    @JoinTable(
        name = "onlineorder_medicine",
        joinColumns = @JoinColumn(name = "onlineorder_id"),
        inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    private List<Medicine> items = new ArrayList<>();

    // Relacja z Customer
    @ManyToOne
    private Customer customer;

    public OnlineOrder() {
    }

    public OnlineOrder(List<Medicine> items, Customer customer) {
        this.items = items;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Medicine> getItems() {
        return items;
    }

    public void setItems(List<Medicine> items) {
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void processOrder() {
        if (customer != null) {
            System.out.println("Order " + id + " processed for customer: " + customer.getName());
        } else {
            System.out.println("Order " + id + " processed (customer unknown)");
        }
    }
}
