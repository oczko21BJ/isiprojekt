package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Klasa reprezentująca promocję w systemie zarządzania apteką.
 * 
 * <p>Klasa Promotion przechowuje informacje o promocji,
 * w tym nazwę, opis, wartość zniżki oraz daty rozpoczęcia i zakończenia promocji.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Promotion implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double discountValue; // np. 0.10 dla 10% znizki

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean manuallyDeactivated = false;

    public Promotion() {
    }

    public Promotion(String name, String description, Double discountValue, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.description = description;
        this.discountValue = discountValue;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        LocalDate today = LocalDate.now();
        return (startDate == null || !today.isBefore(startDate)) &&
               (endDate == null || !today.isAfter(endDate));
    }

    public void applyPromotion(Medicine medicine) {
        if (isActive() && medicine != null) {
            double newPrice = medicine.price * (1 - discountValue);
            medicine.changePrice((float) newPrice);
            System.out.println("Promotion applied to " + medicine.name + ": new price = " + newPrice);
        }
    }

    
public void removePromotion() {
    this.manuallyDeactivated = true;
}

   public boolean isPromotionActive() {
    return !manuallyDeactivated && isActive();
}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
