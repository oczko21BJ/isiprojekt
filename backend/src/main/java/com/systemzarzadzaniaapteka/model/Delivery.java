package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Klasa reprezentująca dostawę w systemie zarządzania apteką.
 * 
 * <p>Klasa Delivery przechowuje informacje o dostawie związanej z zamówieniem online,
 * w tym datę dostawy oraz powiązanie z konkretnym zamówieniem.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Delivery {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private OnlineOrder order;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;

    public Delivery() {
    }

    public Delivery(OnlineOrder order, Date deliveryDate) {
        this.order = order;
        this.deliveryDate = deliveryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OnlineOrder getOrder() {
        return order;
    }

    public void setOrder(OnlineOrder order) {
        this.order = order;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void scheduleDelivery() {
        if (order != null) {
            System.out.println("Delivery scheduled for order " + order.getId() + " on " + deliveryDate);
        } else {
            System.out.println("Delivery scheduled on " + deliveryDate);
        }
    }
}
