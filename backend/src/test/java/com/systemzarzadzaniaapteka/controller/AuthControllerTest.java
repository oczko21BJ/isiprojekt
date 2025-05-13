package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.model.AppUser;
import com.systemzarzadzaniaapteka.security.CustomOidcUser;
import com.systemzarzadzaniaapteka.security.JwtTokenProvider;
import com.systemzarzadzaniaapteka.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
/**
 * Klasa testowa dla kontrolera {@link AuthController}, która testuje funkcjonalności związane z
 * autentykacją użytkownika oraz pobieraniem danych aktualnie zalogowanego użytkownika.
 *
 * Testy obejmują przypadki poprawnej i niepoprawnej autentykacji użytkownika, a także możliwość
 * pobrania danych zalogowanego użytkownika.
 */
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthController authController;
    /**
     * Test przypadku udanej autentykacji użytkownika.
     *
     * Test sprawdza, czy po podaniu poprawnych danych logowania proces autentykacji zwraca
     * odpowiedź z statusem sukcesu, tokenem JWT oraz danymi użytkownika.
     */
    @Test
    void authenticateUser_Success() {
        // Given
        AppUser mockUser = new AppUser();
        mockUser.setId(1L);
        mockUser.setName("Jan Kowalski");
        mockUser.setEmail("jan@example.com");
        mockUser.setRole("USER");

        when(userService.authenticate(anyString(), anyString()))
                .thenReturn(mockUser);
        when(jwtTokenProvider.generateToken(any(AppUser.class)))
                .thenReturn("mocked-jwt-token");

        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", "jan@example.com");
        loginData.put("password", "password123");

        // Reczne wstrzykniecie JwtTokenProvider
        authController.jwtTokenProvider = jwtTokenProvider;

        // When
        ResponseEntity<?> response = authController.authenticateUser(loginData);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);

        Map<?, ?> responseBody = (Map<?, ?>) response.getBody();
        assertTrue((Boolean) responseBody.get("success"));
        assertEquals("Authentication successful", responseBody.get("message"));
        assertEquals("mocked-jwt-token", responseBody.get("accessToken"));

        Map<?, ?> userData = (Map<?, ?>) responseBody.get("user");
        assertEquals(1L, userData.get("id"));
        assertEquals("Jan Kowalski", userData.get("name"));
        assertEquals("jan@example.com", userData.get("email"));
        assertEquals("USER", userData.get("role"));
    }
    /**
     * Test przypadku braku danych logowania.
     *
     * Test sprawdza, czy serwis zwraca błąd, gdy użytkownik nie poda wymaganych danych logowania (email i hasło).
     */
    @Test
    void authenticateUser_MissingCredentials() {
        // Given
        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", "jan@example.com");

        // When
        ResponseEntity<?> response = authController.authenticateUser(loginData);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertFalse((Boolean) body.get("success"));
        assertEquals("Email and password are required", body.get("message"));
    }
    /**
     * Test przypadku niepoprawnych danych logowania.
     *
     * Test sprawdza, czy serwis zwraca błąd w przypadku niepoprawnych danych logowania (np. złe hasło).
     */
    @Test
    void authenticateUser_InvalidCredentials() {
        // Given
        when(userService.authenticate(anyString(), anyString()))
                .thenThrow(new RuntimeException("Invalid credentials"));

        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", "jan@example.com");
        loginData.put("password", "wrong-password");

        // When
        ResponseEntity<?> response = authController.authenticateUser(loginData);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertFalse((Boolean) body.get("success"));
        assertEquals("Invalid email or password", body.get("message"));
    }
    /**
     * Test przypadku pobrania danych zalogowanego użytkownika.
     *
     * Test sprawdza, czy po zalogowaniu użytkownika (np. poprzez token OIDC) serwis poprawnie
     * zwraca dane tego użytkownika.
     */
    @Test
    void getCurrentUser_Authenticated() {
        // Given
        AppUser mockUser = new AppUser();
        mockUser.setId(1L);
        mockUser.setName("Jan Kowalski");
        mockUser.setEmail("jan@example.com");
        mockUser.setRole("USER");

        // Tworzenie wymaganych argumentow dla CustomOidcUser
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "123");
        claims.put("name", "Jan Kowalski");

        OidcIdToken idToken = new OidcIdToken(
                "token-value",
                Instant.now(),
                Instant.now().plusSeconds(3600),
                claims
        );

        OidcUserInfo userInfo = new OidcUserInfo(claims);

        CustomOidcUser principal = new CustomOidcUser(
                mockUser,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                idToken,
                userInfo
        );

        // When
        ResponseEntity<?> response = authController.getCurrentUser(principal);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertTrue((Boolean) body.get("success"));

        Map<?, ?> userData = (Map<?, ?>) body.get("user");
        assertEquals(1L, userData.get("id"));
        assertEquals("Jan Kowalski", userData.get("name"));
        assertEquals("jan@example.com", userData.get("email"));
        assertEquals("USER", userData.get("role"));
    }
    /**
     * Test przypadku próby pobrania danych użytkownika, gdy użytkownik nie jest zalogowany.
     *
     * Test sprawdza, czy serwis zwraca błąd, gdy użytkownik nie jest zalogowany i próbuje uzyskać swoje dane.
     */
    @Test
    void getCurrentUser_Unauthenticated() {
        // When
        ResponseEntity<?> response = authController.getCurrentUser(null);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertFalse((Boolean) body.get("success"));
        assertEquals("User not authenticated", body.get("message"));
    }
}