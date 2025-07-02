package com.systemzarzadzaniaapteka.model;

/**
 * Klasa reprezentująca zamiar płatności w systemie zarządzania apteką.
 * 
 * <p>Klasa PaymentIntent przechowuje informacje o zamiarze płatności,
 * w tym identyfikator, klucz klienta, kwotę, walutę oraz status płatności.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
public class PaymentIntent {
    private String id;
    private String clientSecret;
    private Long amount;
    private String currency;
    private String status;

    public PaymentIntent() {}

    public PaymentIntent(String id, String clientSecret, Long amount, String currency, String status) {
        this.id = id;
        this.clientSecret = clientSecret;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
