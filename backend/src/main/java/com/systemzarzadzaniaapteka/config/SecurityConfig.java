package com.systemzarzadzaniaapteka.config;

import com.systemzarzadzaniaapteka.service.UserService;
import com.systemzarzadzaniaapteka.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.context.annotation.Lazy;
import com.systemzarzadzaniaapteka.model.AppUser;
import com.systemzarzadzaniaapteka.security.CustomOidcUser;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
/**
 * Konfiguracja bezpieczeństwa aplikacji.
 * Definiuje ustawienia zabezpieczeń, takie jak uwierzytelnianie JWT, obsługa OAuth2,
 * zarządzanie CORS oraz autoryzacja żądań HTTP.
 */
public class SecurityConfig {

  private final UserService userService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(@Lazy UserService userService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userService = userService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                    corsConfig.setAllowedOrigins(java.util.List.of("http://localhost:8081")); // lub "*"
                    corsConfig.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
                    corsConfig.setAllowedHeaders(java.util.List.of("*"));
                    corsConfig.setAllowCredentials(true); // jesli korzystasz z ciasteczek/autoryzacji
                    return corsConfig;
                }))
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                        "/",
                                        "/api/users/authenticate",
                                        "/api/users/authenticate/google",
                                        "/api/users/register",
                                        "/api/oauth2/**"
                                ).permitAll()
                                .anyRequest().authenticated())



                        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


  /**
     * Obsluga uzytkownika po zalogowaniu przez OAuth2 (np. Google).
     */
    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return request -> {
            System.out.println("CUSTOM OIDC SERVICE STARTED!");
            OidcUserService delegate = new OidcUserService();
            OidcUser oidcUser = delegate.loadUser(request);
            Map<String, Object> attributes = oidcUser.getAttributes();

            // Przyklad: pobieranie emaila i imienia z atrybutow Google
            String email = (String) attributes.get("email");
            String name = (String) attributes.get("name");
            //  logi
            System.out.println("OIDC received email: " + email);
            System.out.println("OIDC received name: " + name);
            // Zarejestruj uzytkownika lub pobierz z bazy
            AppUser appUser = userService.processOAuth2User(email, name);
            System.out.println("Loaded appUser: " + appUser);

            // Przypisz role (np. CLIENT)
            return new CustomOidcUser(
                    appUser,
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_" + appUser.getRole())),
                oidcUser.getIdToken(),
                oidcUser.getUserInfo()
            );
        };
    }

 /**
     * Po udanym logowaniu przez OAuth2 przekieruj uzytkownika na wybrana strone.
     */
    private AuthenticationSuccessHandler oauth2SuccessHandler() {
        return (request, response, authentication) -> {
            Object principal = authentication.getPrincipal();
            System.out.println("✅ principal: " + principal);
            System.out.println("✅ class: " + principal.getClass().getName());
            response.sendRedirect("/api/cart");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*") // w produkcji trzeba konkretny adres
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");

            }
        };
    }
}
