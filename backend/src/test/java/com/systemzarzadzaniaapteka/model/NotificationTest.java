package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NotificationTest {

    @Test
    void shouldCreateNotification() {
        Notification n = new Notification();
        n.setMessage("Wiadomosc");
        assertEquals("Wiadomosc", n.getMessage());
    }
}