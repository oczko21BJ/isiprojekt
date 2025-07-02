package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.model.Promotion;
import com.systemzarzadzaniaapteka.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

/**
 * Klasa kontrolera do zarządzania promocjami w systemie zarządzania apteką.
 * 
 * <p>Klasa PromotionController zapewnia endpointy do obsługi promocji,
 * w tym pobieranie wszystkich promocji, pobieranie promocji po ID,
 * tworzenie nowych promocji, aktualizowanie istniejących promocji
 * oraz usuwanie promocji.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping
    public List<Promotion> getAll() {
        return promotionService.getAll();
    }

    @GetMapping("/{id}")
    public Promotion getById(@PathVariable Long id) {
        return promotionService.getById(id);
    }

    @PostMapping
    public Promotion create(@RequestBody @Valid Promotion promotion) {
        return promotionService.save(promotion);
    }

    @PutMapping("/{id}")
    public Promotion update(@PathVariable Long id, @RequestBody @Valid Promotion promotion) {
        return promotionService.update(id, promotion);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        promotionService.delete(id);
    }
}
