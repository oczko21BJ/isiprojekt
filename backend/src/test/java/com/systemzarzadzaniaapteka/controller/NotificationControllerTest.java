package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.model.Notification;
import com.systemzarzadzaniaapteka.service.NotificationService;
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
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void getAll() {
        // Given
        Notification notif1 = new Notification();
        Notification notif2 = new Notification();
        when(notificationService.getAll()).thenReturn(Arrays.asList(notif1, notif2));

        // When
        List<Notification> result = notificationController.getAll();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void send() {
        // Given
        Notification notification = new Notification();
        when(notificationService.send(any(Notification.class))).thenReturn(notification);

        // When
        Notification result = notificationController.send(notification);

        // Then
        assertEquals(notification, result);
    }

    @Test
    void delete() {
        // Given
        Long id = 1L;

        // When
        notificationController.delete(id);

        // Then
        verify(notificationService).delete(id);
    }
}