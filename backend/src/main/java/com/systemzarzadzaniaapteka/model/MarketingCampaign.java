package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca kampanię marketingową w systemie zarządzania apteką.
 * 
 * <p>Klasa MarketingCampaign przechowuje informacje o kampanii marketingowej,
 * w tym nazwę, opis, daty rozpoczęcia i zakończenia oraz grupę docelową klientów.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class MarketingCampaign {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToMany
    private List<Customer> targetAudience = new ArrayList<>();

    public MarketingCampaign() {
    }

    public MarketingCampaign(String name, String description, LocalDate startDate, LocalDate endDate, List<Customer> targetAudience) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.targetAudience = targetAudience;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Customer> getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(List<Customer> targetAudience) {
        this.targetAudience = targetAudience;
    }

    public void launchCampaign() {
        System.out.println("Launching campaign: " + name + " for " + targetAudience.size() + " customers.");
    }
}
