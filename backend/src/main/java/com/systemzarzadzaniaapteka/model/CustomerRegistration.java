package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa reprezentująca rejestrację klienta w systemie zarządzania apteką.
 * 
 * <p>Klasa CustomerRegistration przechowuje szczegółowe informacje o kliencie
 * w formie mapy klucz-wartość, umożliwiając rejestrację nowego klienta w systemie.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class CustomerRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Przykladowe pole na szczegoly klienta – jako JSON lub klucz-wartosc
    @ElementCollection
    @CollectionTable(name = "customer_registration_details", joinColumns = @JoinColumn(name = "registration_id"))
    @MapKeyColumn(name = "detail_key")
    @Column(name = "detail_value")
    private Map<String, String> customerDetails = new HashMap<>();

    public CustomerRegistration() {
    }

    public CustomerRegistration(Map<String, String> customerDetails) {
        this.customerDetails = customerDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(Map<String, String> customerDetails) {
        this.customerDetails = customerDetails;
    }

    public void registerCustomer() {
        System.out.println("Registering new customer.");
    }
}
