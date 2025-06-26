package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.model.Promotion;
import com.systemzarzadzaniaapteka.service.PromotionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PromotionControllerTest {

    @Mock
    private PromotionService promotionService;

    @InjectMocks
    private PromotionController promotionController;

    @Test
    void getAll() {
        // Given
        Promotion promo1 = new Promotion();
        Promotion promo2 = new Promotion();
        when(promotionService.getAll()).thenReturn(Arrays.asList(promo1, promo2));

        // When
        List<Promotion> result = promotionController.getAll();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void getById() {
        // Given
        Long id = 1L;
        Promotion promotion = new Promotion();
        when(promotionService.getById(id)).thenReturn(promotion);

        // When
        Promotion result = promotionController.getById(id);

        // Then
        assertEquals(promotion, result);
    }

    @Test
    void create() {
        // Given
        Promotion promotion = new Promotion();
        when(promotionService.save(any(Promotion.class))).thenReturn(promotion);

        // When
        Promotion result = promotionController.create(promotion);

        // Then
        assertEquals(promotion, result);
    }

    @Test
    void update() {
        // Given
        Long id = 1L;
        Promotion promotion = new Promotion();
        when(promotionService.update(eq(id), any(Promotion.class))).thenReturn(promotion);

        // When
        Promotion result = promotionController.update(id, promotion);

        // Then
        assertEquals(promotion, result);
    }

    @Test
    void delete() {
        // Given
        Long id = 1L;

        // When
        promotionController.delete(id);

        // Then
        verify(promotionService).delete(id);
    }
}