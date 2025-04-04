package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @Test
    void testCartCreationAndSetters() {
        // When
        Cart cart = new Cart();
        cart.setId(1L);

        AppUser user = new AppUser();
        user.setName("Jan Kowalski");
        cart.setUser(user);

        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem());
        cart.setItems(items);

        // Then
        assertEquals(1L, cart.getId());
        assertEquals(user, cart.getUser());
        assertEquals(items, cart.getItems());
    }

    @Test
    void testRemoveItem() {
        // Given
        Cart cart = new Cart();
        CartItem item = new CartItem();
        cart.getItems().add(item);
        item.setCart(cart);

        // When
        cart.removeItem(item);

        // Then
        assertFalse(cart.getItems().contains(item));
        assertNull(item.getCart());
    }

    @Test
    void testGetTotalAmount() {
        // Given
        Cart cart = new Cart();

        Medicine medicine1 = new Medicine();
        medicine1.setPrice(10.0F);
        CartItem item1 = new CartItem();
        item1.setMedicine(medicine1);
        item1.setQuantity(2);

        Medicine medicine2 = new Medicine();
        medicine2.setPrice(5.0F);
        CartItem item2 = new CartItem();
        item2.setMedicine(medicine2);
        item2.setQuantity(3);

        cart.getItems().add(item1);
        cart.getItems().add(item2);

        // When
        double total = cart.getTotalAmount();

        // Then
        assertEquals(35.0, total); // (10*2) + (5*3) = 20 + 15 = 35
    }
}