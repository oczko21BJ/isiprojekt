package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class AnalyticsTest {

    @Test
    void testAnalyticsCreationAndSetters() {
        // When
        Analytics analytics = new Analytics();
        analytics.setId(1L);
        analytics.setDataJson("{'visits':1200}");

        Map<String, Object> data = new HashMap<>();
        data.put("visits", 1200);
        analytics.setData(data);

        // Then
        assertEquals(1L, analytics.getId());
        assertEquals("{'visits':1200}", analytics.getDataJson());
        assertEquals(data, analytics.getData());
    }

    @Test
    void testConstructorWithParameters() {
        // Given
        Map<String, Object> data = new HashMap<>();
        data.put("sales", 45000.0);

        // When
        Analytics analytics = new Analytics(data);

        // Then
        assertEquals(data, analytics.getData());
    }

    @Test
    void testGenerateInsights() {
        // Given
        Analytics analytics = new Analytics();

        // When & Then
        assertDoesNotThrow(analytics::generateInsights);
    }
}