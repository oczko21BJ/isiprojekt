package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca koszyk zakupowy w systemie zarządzania apteką.
 * 
 * <p>Klasa Cart przechowuje informacje o koszyku zakupowym użytkownika,
 * w tym listę przedmiotów w koszyku oraz powiązanie z konkretnym użytkownikiem.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private AppUser user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public Cart() {}

    // Gettery i settery
    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Usuwanie przedmiotu z koszyka
    public void removeItem(CartItem item) {
        this.items.remove(item);
        item.setCart(null); // Usuwamy powiazanie z koszykiem, zeby zachowac integralnosc
    }

    // Obliczanie calkowitej wartosci koszyka
    public Double getTotalAmount() {
        return items.stream()
                .mapToDouble(item -> item.getMedicine().getPrice() * item.getQuantity()) // Zakladajac, ze masz metode getPrice() w klasie Medicine
                .sum();
    }
}
