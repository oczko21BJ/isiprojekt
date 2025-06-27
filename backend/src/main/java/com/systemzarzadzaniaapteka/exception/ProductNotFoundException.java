// ProductNotFoundException.java
package com.systemzarzadzaniaapteka.exception;

/**
 * Wyjątek rzucany, gdy produkt nie zostanie znaleziony w systemie.
 * Informuje o braku żądanego produktu w bazie danych lub magazynie.
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
