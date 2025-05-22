package com.systemzarzadzaniaapteka.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca zarządzanie promocjami w systemie zarządzania apteką.
 * 
 * <p>Klasa PromotionManagement przechowuje informacje o zarządzaniu promocjami,
 * w tym listę promocji oraz nazwisko menedżera odpowiedzialnego za zarządzanie.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class PromotionManagement {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Lista powiazanych promocji
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Promotion> promotions = new ArrayList<>();

    private String managerName;

    public PromotionManagement() {
    }

    public PromotionManagement(String managerName) {
        this.managerName = managerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public void addPromotion(Promotion promotion) {
        promotions.add(promotion);
        System.out.println("Promotion added: " + promotion.getName());
    }

    public void removePromotion(Promotion promotion) {
        promotions.remove(promotion);
        System.out.println("Promotion removed: " + promotion.getName());
    }

    public void listPromotions() {
        System.out.println("Promotions managed by " + managerName + ":");
        for (Promotion promo : promotions) {
            System.out.println(" - " + promo.getName());
        }
    }

    public void editPromotion(Promotion promotion) {
    }

}
