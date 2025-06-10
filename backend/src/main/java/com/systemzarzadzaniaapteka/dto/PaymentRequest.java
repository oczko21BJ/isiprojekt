package com.systemzarzadzaniaapteka.dto;

/**
 * Klasa DTO reprezentująca żądanie płatności.
 * Zawiera dane niezbędne do przetworzenia płatności, takie jak identyfikator zamówienia,
 * metoda płatności oraz informacje o karcie płatniczej.
 */
public class PaymentRequest {
    private Long orderId;
    private String paymentMethod;
    private float amount;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
    
    public PaymentRequest() {
    }
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public float getAmount() {
        return amount;
    }
    
    public void setAmount(float amount) {
        this.amount = amount;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public String getCardHolderName() {
        return cardHolderName;
    }
    
    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }
    
    public String getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public String getCvv() {
        return cvv;
    }
    
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
