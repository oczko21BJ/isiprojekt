package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PromotionTest {

    @Test
    void shouldApplyPromotion() {
        Promotion promo = new Promotion();
        promo.setDescription("Letnia promocja");

        assertEquals("Letnia promocja", promo.getDescription());
    }
}
