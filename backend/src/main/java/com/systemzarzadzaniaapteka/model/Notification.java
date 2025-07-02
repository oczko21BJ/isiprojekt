package com.systemzarzadzaniaapteka.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Klasa reprezentująca powiadomienie w systemie zarządzania apteką.
 * 
 * <p>Klasa Notification przechowuje informacje o powiadomieniu wysyłanym do użytkownika,
 * w tym treść wiadomości, datę wysłania oraz odbiorcę powiadomienia.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private LocalDateTime sentAt;

    @ManyToOne
    private AppUser recipient;

    public Notification() {
    }

    public Notification(String message, AppUser recipient) {
        this.message = message;
        this.recipient = recipient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AppUser getRecipient() {
        return recipient;
    }

    public void setRecipient(AppUser recipient) {
        this.recipient = recipient;
    }
    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }

    public void sendNotification() {
        this.sentAt = LocalDateTime.now();

        if (recipient != null) {
            System.out.println("Sending notification to " + recipient.getName() + ": " + message);
        } else {
            System.out.println("Notification: " + message);
        }
    }
}
