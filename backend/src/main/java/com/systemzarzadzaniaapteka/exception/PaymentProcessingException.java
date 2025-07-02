package com.systemzarzadzaniaapteka.exception;

/**
 * Wyjątek rzucany, gdy wystąpi problem podczas przetwarzania płatności.
 * Informuje o niepowodzeniu operacji płatniczej, podając przyczynę błędu.
 */
public class PaymentProcessingException extends RuntimeException {
    public PaymentProcessingException(String message) {
        super(message);
    }

    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
