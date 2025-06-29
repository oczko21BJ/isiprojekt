package com.systemzarzadzaniaapteka.model;
import jakarta.persistence.*;

/**
 * Klasa reprezentująca reklamację dotyczącą leku w systemie zarządzania apteką.
 * 
 * <p>Klasa DrugComplaint przechowuje informacje o reklamacji zgłoszonej przez klienta
 * dotyczącej konkretnego leku, w tym opis problemu oraz dane zgłaszającego.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class DrugComplaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    private Customer reportedBy;

    public DrugComplaint() {
    }

    public DrugComplaint(String description, Customer reportedBy) {
        this.description = description;
        this.reportedBy = reportedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Customer getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(Customer reportedBy) {
        this.reportedBy = reportedBy;
    }

    public void submitComplaint() {
        System.out.println("Complaint submitted by " 
            + (reportedBy != null ? reportedBy.getName() : "unknown") 
            + ": " + description);
    }
}
