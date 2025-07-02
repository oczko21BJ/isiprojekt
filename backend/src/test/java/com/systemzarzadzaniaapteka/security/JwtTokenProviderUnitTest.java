// src/test/java/com/systemzarzadzaniaapteka/security/JwtTokenProviderUnitTest.java
package com.systemzarzadzaniaapteka.security;

import com.systemzarzadzaniaapteka.model.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderUnitTest {

    private JwtTokenProvider provider;

    @BeforeEach
    void init() {
        provider = new JwtTokenProvider();
        provider.init(); // generuje klucz
    }

    @Test
    void generateAndValidate() {
        AppUser u = new AppUser("Test","t@t.pl","555","CLIENT","pw");
        u.setId(42L);

        String token = provider.generateToken(u);
        assertNotNull(token);
        assertTrue(provider.validateToken(token));
        assertEquals(42L, provider.getUserIdFromJWT(token));
    }
}
