package com.systemzarzadzaniaapteka.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Klasa reprezentująca dostawcę w systemie zarządzania apteką.
 * 
 * <p>Klasa Supplier przechowuje informacje o dostawcy leków,
 * w tym nazwę, dane kontaktowe oraz listę dostarczanych leków.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String contactInfo;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Medicine> suppliedMedicines = new ArrayList<>();

    public Supplier() {
    }

    public Supplier(String name, String contactInfo) {
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
        medicine.setSupplier(this);
        System.out.println("Medicine " + medicine.getName() + " supplied by supplier: " + name);
    }

    public void provideMedicine() {
        System.out.println("Supplier " + name + " provides medicine.");
    }
}
