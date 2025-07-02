package com.systemzarzadzaniaapteka.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class SecurityConfigTest {

    @Test
    void shouldReturnPasswordEncoder() {
        SecurityConfig config = new SecurityConfig(null, null);
        PasswordEncoder encoder = config.passwordEncoder();
        assertNotNull(encoder);
        assertTrue(encoder.matches("password", encoder.encode("password")));
    }
}