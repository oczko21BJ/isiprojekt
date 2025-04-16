package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ReminderTest {

    @Test
    void testReminderCreationAndSetters() {
        // Given
        AppUser user = new AppUser();
        user.setName("Jan Kowalski");
        LocalDateTime remindAt = LocalDateTime.now().plusHours(1);

        // When
        Reminder reminder = new Reminder();
        reminder.setId(1L);
        reminder.setMessage("Przypomnienie o wizycie");
        reminder.setRemindAt(remindAt);
        reminder.setRecipient(user);

        // Then
        assertEquals(1L, reminder.getId());
        assertEquals("Przypomnienie o wizycie", reminder.getMessage());
        assertEquals(remindAt, reminder.getRemindAt());
        assertEquals(user, reminder.getRecipient());
    }

    @Test
    void testSendReminderWithRecipient() {
        // Given
        AppUser user = new AppUser();
        user.setName("Anna Nowak");
        Reminder reminder = new Reminder("Badanie kontrolne", LocalDateTime.now(), user);

        // When & Then
        assertDoesNotThrow(reminder::sendReminder);
    }

    @Test
    void testSendReminderWithoutRecipient() {
        // Given
        Reminder reminder = new Reminder("Badanie kontrolne", LocalDateTime.now(), null);

        // When & Then
        assertDoesNotThrow(reminder::sendReminder);
    }
}