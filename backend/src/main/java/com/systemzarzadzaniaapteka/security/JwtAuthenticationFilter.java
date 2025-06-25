package com.systemzarzadzaniaapteka.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.systemzarzadzaniaapteka.service.UserService;
import com.systemzarzadzaniaapteka.security.JwtTokenProvider;
import com.systemzarzadzaniaapteka.model.AppUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.context.annotation.Lazy;

@Component
/**
 * Filtr uwierzytelniania JWT.
 * Odpowiada za sprawdzanie i walidację tokenów JWT w nagłówkach żądań HTTP,
 * ustawiając kontekst bezpieczeństwa dla uwierzytelnionych użytkowników.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, @Lazy UserService userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String token = null;
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
        }
        System.out.println("➡️ JWT Filter: Authorization header: " + header);
        if (token != null && tokenProvider.validateToken(token)) {
            System.out.println("✅ Token is valid");
            Long userId = tokenProvider.getUserIdFromJWT(token);
            System.out.println("🆔 Extracted userId: " + userId);

            AppUser userDetails = userService.getById(userId);
            if (userDetails == null) {
                System.out.println("❌ User not found in DB for ID: " + userId);
            } else {
                System.out.println("✅ User loaded: " + userDetails.getEmail());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("🔐 Authentication set in SecurityContext");
            }
        } else {
            System.out.println("❌ Token is missing or invalid");
        }
        filterChain.doFilter(request, response);
    }
}
