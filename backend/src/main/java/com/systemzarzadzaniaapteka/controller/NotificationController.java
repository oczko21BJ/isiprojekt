package com.systemzarzadzaniaapteka.controller;
import com.systemzarzadzaniaapteka.model.Notification;
import com.systemzarzadzaniaapteka.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Klasa kontrolera do zarządzania powiadomieniami w systemie zarządzania apteką.
 * 
 * <p>Klasa NotificationController zapewnia endpointy do obsługi powiadomień,
 * w tym pobieranie wszystkich powiadomień, wysyłanie nowych powiadomień
 * oraz usuwanie powiadomień.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<Notification> getAll() {
        return notificationService.getAll();
    }

    @PostMapping
    public Notification send(@RequestBody @Valid Notification notification) {
        return notificationService.send(notification);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificationService.delete(id);
    }
}
