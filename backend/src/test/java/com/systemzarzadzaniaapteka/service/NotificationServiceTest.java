package com.systemzarzadzaniaapteka.service;

import com.systemzarzadzaniaapteka.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
        notification.setMessage("Test message");
    }

    @Test
    void getAll() {
        notificationService.send(notification);
        List<Notification> result = notificationService.getAll();
        
        assertEquals(1, result.size());
        assertEquals("Test message", result.get(0).getMessage());
    }

    @Test
    void send() {
        Notification result = notificationService.send(notification);
        
        assertNotNull(result.getId());
        assertNotNull(result.getSentAt());
        assertEquals("Test message", result.getMessage());
    }

    @Test
    void delete() {
        Notification sent = notificationService.send(notification);
        notificationService.delete(sent.getId());
        
        assertEquals(0, notificationService.getAll().size());
    }
}