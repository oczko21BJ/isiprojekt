package com.systemzarzadzaniaapteka.model;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Klasa reprezentująca elektroniczną receptę w systemie zarządzania apteką.
 * 
 * <p>Klasa EPrescription przechowuje informacje o elektronicznej recepcie,
 * w tym kod recepty, listę leków oraz datę ważności recepty.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class EPrescription implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @ManyToMany
    private List<Medicine> medicines;

    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    public EPrescription() {
    }

    public EPrescription(String code, List<Medicine> medicines, Date expirationDate) {
        this.code = code;
        this.medicines = medicines;
        this.expirationDate = expirationDate;
    }

    // Builder pattern for EPrescription
    public static class Builder {
        private String code;
        private List<Medicine> medicines;
        private Date expirationDate;

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }
        @Transient
        public Builder setMedicines(List<Medicine> medicines) {
            this.medicines = medicines;
            return this;
        }

        public Builder setExpirationDate(Date expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public EPrescription build() {
            return new EPrescription(code, medicines, expirationDate);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean validateCode() {
        return code != null && code.matches("EP-[0-9]{4}");
    }

    public void fulfillPrescription() {
        System.out.println("Prescription " + code + " fulfilled.");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
