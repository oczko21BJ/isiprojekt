// src/test/java/com/systemzarzadzaniaapteka/config/MockJwtFilter.java
package com.systemzarzadzaniaapteka.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * Zastepuje JwtAuthenticationFilter w testach (nie wymaga tokenu).
 */
@Component
public class MockJwtFilter extends OncePerRequestFilter {
    @Override protected void doFilterInternal(HttpServletRequest r,
                                             HttpServletResponse s,
                                             FilterChain chain)
            throws ServletException, IOException {
        chain.doFilter(r, s);
    }
}
