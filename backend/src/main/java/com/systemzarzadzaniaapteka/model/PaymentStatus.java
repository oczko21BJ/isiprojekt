package com.systemzarzadzaniaapteka.model;

/**
 * Enum reprezentujący status płatności w systemie zarządzania apteką.
 * 
 * <p>Enum PaymentStatus definiuje możliwe stany płatności, takie jak oczekująca,
 * zapłacona, nieudana, anulowana oraz zakończona.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
public enum PaymentStatus {
    PENDING, PAID, FAILED, CANCELLED, COMPLETED
}
