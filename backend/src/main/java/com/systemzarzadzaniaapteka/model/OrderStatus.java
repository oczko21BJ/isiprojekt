package com.systemzarzadzaniaapteka.model;

/**
 * Enum reprezentujący status zamówienia w systemie zarządzania apteką.
 * 
 * <p>Enum OrderStatus definiuje możliwe stany zamówienia, takie jak oczekujące,
 * w trakcie realizacji, zakończone, anulowane oraz zapłacone.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
public enum OrderStatus {
    PENDING, PROCESSING, COMPLETED, CANCELLED, PAID
}
