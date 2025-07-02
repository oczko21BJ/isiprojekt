package com.systemzarzadzaniaapteka.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca kontrolę stanu magazynowego w systemie zarządzania apteką.
 * 
 * <p>Klasa InventoryCheck przechowuje informacje o przeprowadzonej kontroli stanu magazynowego,
 * w tym datę kontroli, osobę odpowiedzialną za kontrolę oraz listę sprawdzonych leków.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class InventoryCheck {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Uzycie konwertera dla LocalDateTime
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime checkDate;
  //  private LocalDateTime checkDate;
  @Transient
  private static InventoryCheck instance;

    @ManyToOne
    private AppUser checkedBy; // Osoba sprawdzajaca stan magazynu

    @ManyToMany
    @JoinTable(
            name = "inventory_check_medicine",
            joinColumns = @JoinColumn(name = "inventory_check_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    private List<Medicine> medicinesChecked = new ArrayList<>();

    public InventoryCheck() {
    }

    public InventoryCheck(LocalDateTime checkDate, AppUser checkedBy, List<Medicine> medicinesChecked) {
        this.checkDate = checkDate;
        this.checkedBy = checkedBy;
        if (medicinesChecked != null) {
            this.medicinesChecked = medicinesChecked;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(LocalDateTime checkDate) {
        this.checkDate = checkDate;
    }

    public AppUser getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(AppUser checkedBy) {
        this.checkedBy = checkedBy;
    }

    public List<Medicine> getMedicinesChecked() {
        return medicinesChecked;
    }

    public void setMedicinesChecked(List<Medicine> medicinesChecked) {
        this.medicinesChecked = medicinesChecked;
    }

    public void updateInventory() {
        System.out.println("Inventory checked by: " + (checkedBy != null ? checkedBy.getName() : "unknown")
                + " on " + checkDate);
        if (medicinesChecked != null) {
            for (Medicine med : medicinesChecked) {
                System.out.println(" - " + med.getName() + ": " + med.getQuantity());
            }
        }
    }
    public static InventoryCheck getInstance() {
        if (instance == null) {
            instance = new InventoryCheck();
        }
        return instance;
    }
}
