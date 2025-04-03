package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.*;

/**
 * Klasa reprezentująca zniżkę w systemie zarządzania apteką.
 * 
 * <p>Klasa Discount przechowuje informacje o zniżce, w tym jej wartość
 * oraz powiązanie z konkretnym lekiem, na który zniżka jest stosowana.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Discount implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private float value; // Wartosc znizki, np. 0.1 dla 10%

    @ManyToOne
    private Medicine medicine;

    public Discount() {
    }

    public Discount(int id, float value, Medicine medicine) {
        this.id = id;
        this.value = value;
        this.medicine = medicine;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public void applyDiscount() {
        if (medicine != null) {
            float newPrice = medicine.getPrice() * (1 - value);
            medicine.changePrice(newPrice);
            System.out.println("Discount applied: new price = " + newPrice);
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
