package com.systemzarzadzaniaapteka.model;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Klasa reprezentująca płatność w systemie zarządzania apteką.
 * 
 * <p>Klasa Payment przechowuje informacje o płatności związanej z zamówieniem,
 * w tym kwotę, status płatności, metodę płatności oraz datę transakcji.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonIgnoreProperties("payments")
    private Order order;

    private BigDecimal amount;
    private String stripePaymentIntentId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    private String paymentMethod;
    private String transactionId;
    private LocalDateTime paymentDate;

    public Payment() {
    }
    public Payment(BigDecimal amount, PaymentStatus status) {
        this.amount = amount;
        this.status = status;
    }
    public String getStripePaymentIntentId() {
        return stripePaymentIntentId;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public Order getOrder() {
        return order;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
    public void setStripePaymentIntentId(String stripePaymentIntentId) {
        this.stripePaymentIntentId = stripePaymentIntentId;
    }
    public void processPayment() {
        if (status == PaymentStatus.PENDING) {
            // Logika przetwarzania platnosci
            System.out.println("Processing payment of amount: " + amount);
            // Przykladowo zmieniamy status na COMPLETED
            status = PaymentStatus.COMPLETED;
            System.out.println("Payment completed.");
        } else {
            System.out.println("Payment is not in pending state.");
        }
    }
    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
