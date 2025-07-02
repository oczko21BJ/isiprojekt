package com.systemzarzadzaniaapteka.service;

import com.systemzarzadzaniaapteka.model.Promotion;
import com.systemzarzadzaniaapteka.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Klasa serwisowa do zarządzania promocjami w systemie zarządzania apteką.
 * 
 * <p>Klasa PromotionService zapewnia metody do obsługi promocji,
 * w tym pobieranie wszystkich promocji, pobieranie promocji po ID,
 * zapisywanie nowych promocji, aktualizowanie istniejących promocji
 * oraz usuwanie promocji.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Service
public class PromotionService {

    private final PromotionRepository promotionRepository;

    @Autowired
    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public List<Promotion> getAll() {
        return promotionRepository.findAll();
    }

    public Promotion getById(Long id) {
        return promotionRepository.findById(id).orElse(null);
    }

    public Promotion save(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    public Promotion update(Long id, Promotion promotionDetails) {
        Promotion promotion = promotionRepository.findById(id).orElse(null);
        if (promotion == null) return null;
        promotion.setName(promotionDetails.getName());
        promotion.setDescription(promotionDetails.getDescription());
        promotion.setDiscountValue(promotionDetails.getDiscountValue());
        promotion.setStartDate(promotionDetails.getStartDate());
        promotion.setEndDate(promotionDetails.getEndDate());
        // Dodaj inne pola, jesli sa w modelu Promotion
        return promotionRepository.save(promotion);
    }

    public void delete(Long id) {
        promotionRepository.deleteById(id);
    }
}
