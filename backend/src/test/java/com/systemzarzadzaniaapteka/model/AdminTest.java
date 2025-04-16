package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    void testAdminCreation() {
        // When
        Admin admin = new Admin("Admin", "admin@apteka.pl", "123456789", "Admin", "password123");

        // Then
        assertEquals("Admin", admin.getName());
        assertEquals("admin@apteka.pl", admin.getEmail());
        assertEquals("123456789", admin.getPhone());
        assertEquals("Admin", admin.getRole());
        assertEquals("password123", admin.getPassword());
    }

    @Test
    void testAddSystemUser() {
        // Given
        Admin admin = new Admin();
        AppUser user = new AppUser("User", "user@apteka.pl", "987654321", "User", "password");

        // When & Then
        assertDoesNotThrow(() -> admin.addSystemUser(user));
    }

    @Test
    void testManageAccessRights() {
        // Given
        Admin admin = new Admin();
        AppUser user = new AppUser("User", "user@apteka.pl", "987654321", "User", "password");

        // When & Then
        assertDoesNotThrow(() -> admin.manageAccessRights(user));
    }
}