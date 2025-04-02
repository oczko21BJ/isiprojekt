package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Klasa reprezentująca fakturę w systemie zarządzania apteką.
 * 
 * <p>Klasa Invoice przechowuje informacje o fakturze wystawionej dla klienta,
 * w tym kwotę, datę wystawienia oraz powiązanie z konkretnym klientem.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float amount;

    private LocalDateTime issueDate;

    @ManyToOne
    private Customer customer;

    public Invoice() {
    }

    public Invoice(Float amount, LocalDateTime issueDate, Customer customer) {
        this.amount = amount;
        this.issueDate = issueDate;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void generateInvoice() {
        if (customer != null) {
            System.out.println("Invoice generated for customer: " + customer.getName() + ", amount: " + amount);
        } else {
            System.out.println("Invoice generated, amount: " + amount);
        }
    }
}
