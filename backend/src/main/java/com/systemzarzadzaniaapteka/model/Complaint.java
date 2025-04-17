package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Klasa reprezentująca reklamację w systemie zarządzania apteką.
 * 
 * <p>Klasa Complaint przechowuje informacje o reklamacji zgłoszonej przez klienta,
 * w tym temat, opis, status oraz daty utworzenia i rozwiązania reklamacji.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String subject;

    @NotBlank
    private String description;

    private String status; //  "OPEN", "RESOLVED", "REJECTED"

    private LocalDateTime createdAt;

    private LocalDateTime resolvedAt;

   
    // @ManyToOne
    // private Customer customer;

    public Complaint() {
        this.createdAt = LocalDateTime.now();
        this.status = "OPEN";
    }

    // Gettery i settery

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }
}
