package com.systemzarzadzaniaapteka.service;

import com.systemzarzadzaniaapteka.model.Promotion;
import com.systemzarzadzaniaapteka.repository.PromotionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PromotionServiceTest {

    @Mock
    private PromotionRepository promotionRepository;

    @InjectMocks
    private PromotionService promotionService;

    @Test
    void getAll() {
        Promotion promo1 = new Promotion();
        Promotion promo2 = new Promotion();
        when(promotionRepository.findAll()).thenReturn(List.of(promo1, promo2));

        List<Promotion> result = promotionService.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void getById_Found() {
        Long id = 1L;
        Promotion promotion = new Promotion();
        when(promotionRepository.findById(id)).thenReturn(Optional.of(promotion));

        Promotion result = promotionService.getById(id);

        assertEquals(promotion, result);
    }

    @Test
    void save() {
        Promotion promotion = new Promotion();
        when(promotionRepository.save(promotion)).thenReturn(promotion);

        Promotion result = promotionService.save(promotion);

        assertEquals(promotion, result);
    }

    @Test
    void update_Exists() {
        Long id = 1L;
        Promotion existing = new Promotion();
        Promotion details = new Promotion();
        details.setName("New Promotion");

        when(promotionRepository.findById(id)).thenReturn(Optional.of(existing));
        when(promotionRepository.save(existing)).thenReturn(existing);

        Promotion result = promotionService.update(id, details);

        assertEquals("New Promotion", existing.getName());
    }
}