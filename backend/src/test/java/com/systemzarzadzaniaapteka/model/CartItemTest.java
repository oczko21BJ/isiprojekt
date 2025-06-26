package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {

    @Test
    void testCartItemCreationAndSetters() {
        // When
        CartItem item = new CartItem();
        item.setId(1L);

        Cart cart = new Cart();
        item.setCart(cart);

        Medicine medicine = new Medicine();
        medicine.setName("Apap");
        item.setMedicine(medicine);

        item.setQuantity(5);

        // Then
        assertEquals(1L, item.getId());
        assertEquals(cart, item.getCart());
        assertEquals(medicine, item.getMedicine());
        assertEquals(5, item.getQuantity());
    }

    @Test
    void testGetProductId() {
        // Given
        CartItem item = new CartItem();
        Medicine medicine = new Medicine();
        medicine.setId(10L);
        item.setMedicine(medicine);

        // When
        Long productId = item.getProductId();

        // Then
        assertEquals(10L, productId);
    }

    @Test
    void testGetUnitPrice() {
        // Given
        CartItem item = new CartItem();
        Medicine medicine = new Medicine();
        medicine.setPrice(15.99F);
        item.setMedicine(medicine);

        // When
        float unitPrice = item.getUnitPrice();

        // Then
        assertEquals(15.99, unitPrice, 0.001);
    }
}