package com.systemzarzadzaniaapteka.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca hurtownika w systemie zarządzania apteką.
 * 
 * <p>Klasa Wholesaler przechowuje informacje o hurtowniku leków,
 * w tym nazwę, dane kontaktowe oraz listę dostarczanych leków.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Wholesaler {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String contactInfo;

    @OneToMany(mappedBy = "wholesaler", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medicine> suppliedMedicines = new ArrayList<>();

    public Wholesaler() {
    }

    public Wholesaler(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
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

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<Medicine> getSuppliedMedicines() {
        return suppliedMedicines;
    }

    public void setSuppliedMedicines(List<Medicine> suppliedMedicines) {
        this.suppliedMedicines = suppliedMedicines;
    }

    public void supplyMedicine(Medicine medicine) {
        suppliedMedicines.add(medicine);
        System.out.println("Medicine " + medicine.getName() + " supplied by wholesaler: " + name);
    }

    public void provideStock(Medicine medicine, int quantity) {
        // Proxy pattern could handle remote supplier access
        System.out.println("Providing stock of " + medicine.name + ", quantity: " + quantity);
    }


}
