package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

class AppUserTest {

    @Test
    void testAppUserCreationAndSetters() {
        // When
        AppUser user = new AppUser();
        user.setId(1L);
        user.setName("Jan Kowalski");
        user.setEmail("jan@example.com");
        user.setPhone("123456789");
        user.setRole("USER");
        user.setPassword("password123");

        // Then
        assertEquals(1L, user.getId());
        assertEquals("Jan Kowalski", user.getName());
        assertEquals("jan@example.com", user.getEmail());
        assertEquals("123456789", user.getPhone());
        assertEquals("USER", user.getRole());
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testConstructorWithParameters() {
        // When
        AppUser user = new AppUser("Anna Nowak", "anna@example.com", "987654321", "ADMIN", "admin123");

        // Then
        assertEquals("Anna Nowak", user.getName());
        assertEquals("anna@example.com", user.getEmail());
        assertEquals("987654321", user.getPhone());
        assertEquals("ADMIN", user.getRole());
        assertEquals("admin123", user.getPassword());
    }

    @Test
    void testUserDetailsMethods() {
        // Given
        AppUser user = new AppUser("User", "user@example.com", "111222333", "USER", "pass");

        // When
        String username = user.getUsername();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // Then
        assertEquals("user@example.com", username);
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }

    @Test
    void testLoginLogout() {
        // Given
        AppUser user = new AppUser("User", "user@example.com", "111222333", "USER", "pass");

        // When & Then
        assertDoesNotThrow(user::login);
        assertDoesNotThrow(user::logout);
    }

    @Test
    void testDisplayDetails() {
        // Given
        AppUser user = new AppUser("User", "user@example.com", "111222333", "USER", "pass");

        // When & Then
        assertDoesNotThrow(user::displayDetails);
    }
}