package com.systemzarzadzaniaapteka.model;
import jakarta.persistence.*;

/**
 * Klasa reprezentująca audyt zgodności w systemie zarządzania apteką.
 * 
 * <p>Klasa ComplianceAudit przechowuje informacje o przeprowadzonym audycie zgodności,
 * w tym szczegóły audytu oraz osobę odpowiedzialną za jego wykonanie.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class ComplianceAudit {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String auditDetails;

    @ManyToOne
    private Manager conductedBy;

    public ComplianceAudit() {
    }

    public ComplianceAudit(String auditDetails, Manager conductedBy) {
        this.auditDetails = auditDetails;
        this.conductedBy = conductedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuditDetails() {
        return auditDetails;
    }

    public void setAuditDetails(String auditDetails) {
        this.auditDetails = auditDetails;
    }

    public Manager getConductedBy() {
        return conductedBy;
    }

    public void setConductedBy(Manager conductedBy) {
        this.conductedBy = conductedBy;
    }

    public void performAudit() {
        System.out.println("Audit performed: " + auditDetails +
            (conductedBy != null ? " by " + conductedBy.getName() : ""));
    }
}
