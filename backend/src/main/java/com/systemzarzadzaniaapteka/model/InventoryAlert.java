package com.systemzarzadzaniaapteka.model;
import jakarta.persistence.*;

/**
 * Klasa reprezentująca alert dotyczący stanu magazynowego w systemie zarządzania apteką.
 * 
 * <p>Klasa InventoryAlert przechowuje informacje o alercie dotyczącym stanu magazynowego,
 * w tym komunikat, powiązanie z kontrolą stanu magazynowego oraz konkretnym lekiem.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class InventoryAlert {
    private String message;
    @ManyToOne

    private InventoryCheck triggeredBy;

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // private String message;

  //  private InventoryCheck triggeredBy;

    @ManyToOne
    private Medicine medicine;

    public InventoryAlert() {
    }

    public InventoryAlert(String message, InventoryCheck triggeredBy, Medicine medicine) {
        this.message = message;
        this.triggeredBy = triggeredBy;
        this.medicine = medicine;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public InventoryCheck getTriggeredBy() {
        return triggeredBy;
    }

    public void setTriggeredBy(InventoryCheck triggeredBy) {
        this.triggeredBy = triggeredBy;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public void sendAlert() {
        String medInfo = (medicine != null) ? medicine.getName() : "Unknown medicine";
        System.out.println("Inventory alert for " + medInfo + ": " + message);
    }
}
