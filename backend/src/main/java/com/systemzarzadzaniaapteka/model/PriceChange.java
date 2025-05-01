package com.systemzarzadzaniaapteka.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Klasa reprezentująca zmianę ceny leku w systemie zarządzania apteką.
 * 
 * <p>Klasa PriceChange przechowuje informacje o zmianie ceny leku,
 * w tym starą i nową cenę, datę zmiany oraz osobę odpowiedzialną za zmianę.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class PriceChange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    private Medicine drug;

    @ManyToOne
    private Medicine medicine;

    private Float oldPrice;

    private Float newPrice;

    private LocalDateTime changeDate;

    private String changedBy; // np. nazwa pracownika lub powiazanie z User

    public PriceChange() {
    }

    public PriceChange(Medicine medicine, Float oldPrice, Float newPrice, LocalDateTime changeDate, String changedBy) {
        this.medicine = medicine;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.changeDate = changeDate;
        this.changedBy = changedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Transient
    public Medicine getMedicine() {
        return medicine;
    }
    @Transient
    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Float getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Float oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Float getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Float newPrice) {
        this.newPrice = newPrice;
    }

    public LocalDateTime getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(LocalDateTime changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public void logPriceChange() {
        System.out.println("Price of " + (medicine != null ? medicine.getName() : "unknown medicine")
                + " changed from " + oldPrice + " to " + newPrice + " on " + changeDate
                + " by " + changedBy);
    }
    public PriceChange(Medicine drug, float newPrice) {
        this.drug = drug;
        this.newPrice = newPrice;
    }

    public boolean validatePrice() {
        return newPrice >= 0;
    }

    public void updatePrice() {
        if (validatePrice()) {
            drug.changePrice(newPrice);
        }
    }
}
