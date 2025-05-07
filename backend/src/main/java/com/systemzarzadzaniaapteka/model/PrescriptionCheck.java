package com.systemzarzadzaniaapteka.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Klasa reprezentująca sprawdzenie recepty w systemie zarządzania apteką.
 * 
 * <p>Klasa PrescriptionCheck przechowuje informacje o przeprowadzonym sprawdzeniu recepty,
 * w tym datę sprawdzenia, wynik ważności oraz uwagi dotyczące recepty.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class PrescriptionCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private EPrescription prescription;

    @ManyToOne
    private Pharmacist checkedBy;

    private LocalDateTime checkDate;

    private boolean valid;

    private String notes;

    public PrescriptionCheck() {
    }

    public PrescriptionCheck(EPrescription prescription, Pharmacist checkedBy, LocalDateTime checkDate, boolean valid, String notes) {
        this.prescription = prescription;
        this.checkedBy = checkedBy;
        this.checkDate = checkDate;
        this.valid = valid;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EPrescription getPrescription() {
        return prescription;
    }

    public void setPrescription(EPrescription prescription) {
        this.prescription = prescription;
    }

    public Pharmacist getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(Pharmacist checkedBy) {
        this.checkedBy = checkedBy;
    }

    public LocalDateTime getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(LocalDateTime checkDate) {
        this.checkDate = checkDate;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void performCheck() {
        if (prescription != null && checkedBy != null) {
            this.valid = prescription.validateCode();
            this.checkDate = LocalDateTime.now();
            System.out.println("Prescription " + prescription + " checked by " + checkedBy.getName() + ". Valid: " + valid);
        } else {
            System.out.println("Prescription check failed: missing prescription or pharmacist.");
        }
    }
}
