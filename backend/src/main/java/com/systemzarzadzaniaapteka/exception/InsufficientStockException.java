// InsufficientStockException.java
package com.systemzarzadzaniaapteka.exception;

/**
 * Wyjątek rzucany, gdy nie ma wystarczającej ilości produktu na stanie.
 * Informuje o braku możliwości realizacji zamówienia z powodu niewystarczających zapasów.
 */
public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
