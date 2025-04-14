// src/test/java/com/systemzarzadzaniaapteka/config/TestSecurityConfig.java
package com.systemzarzadzaniaapteka.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import static org.mockito.Mockito.mock;

/**
 * Wylacza rzeczywiste komponenty bezpieczenstwa w testach.
 */
@TestConfiguration
public class TestSecurityConfig {

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        // zwracamy mock, aby pominac konfiguracje hasla itp.
        return mock(AuthenticationManager.class);
    }
}
