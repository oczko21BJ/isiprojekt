package com.systemzarzadzaniaapteka.service;

import com.systemzarzadzaniaapteka.model.Notification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Klasa serwisowa do zarządzania powiadomieniami w systemie zarządzania apteką.
 * 
 * <p>Klasa NotificationService zapewnia metody do obsługi powiadomień,
 * w tym pobieranie wszystkich powiadomień, wysyłanie nowych powiadomień
 * oraz usuwanie powiadomień.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Service
public class NotificationService {

    private final List<Notification> notifications = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<Notification> getAll() {
        return notifications;
    }

    public Notification send(Notification notification) {
        notification.setId(counter.getAndIncrement());
        notification.setSentAt(LocalDateTime.now());
        notifications.add(notification);
        return notification;
    }

    public void delete(Long id) {
        notifications.removeIf(n -> n.getId().equals(id));
    }
}
