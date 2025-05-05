package com.systemzarzadzaniaapteka.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String existingEmail = "jan.kowalski@example.com";
    private final String password = "haslo123";

    @BeforeEach
    void setUp() throws Exception {
        Map<String, Object> user = Map.of(
                "email", existingEmail,
                "password", password,
                "firstName", "Jan",
                "lastName", "Kowalski",
                "phoneNumber", "1234567890"
        );

        var result = mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andReturn();

        int status = result.getResponse().getStatus();
        if (status != 200 && status != 409) {
            throw new RuntimeException("Unexpected registration response: " + status);
        }
    }

    @Test
    void shouldRegisterAndAuthenticateUser() throws Exception {
        String email = "testuser" + System.currentTimeMillis() + "@example.com";

        Map<String, Object> user = Map.of(
                "email", email,
                "password", "securepass",
                "firstName", "Test",
                "lastName", "User",
                "phoneNumber", "123456789"
        );

        // Rejestracja
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        // Logowanie
        mockMvc.perform(post("/api/users/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                Map.of("email", email, "password", "securepass")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists());
    }

    @Test
    void givenExistingUser_whenRegister_thenConflict() throws Exception {
        Map<String, Object> user = Map.of(
                "email", existingEmail,
                "password", password,
                "firstName", "Jan",
                "lastName", "Kowalski",
                "phoneNumber", "1234567890"
        );

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("User already exists"));
    }


    @Test
    void givenValidCredentials_whenAuthenticate_thenReturnsToken() throws Exception {
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String password = "securepass";

        Map<String, Object> user = Map.of(
                "email", email,
                "password", password,
                "firstName", "Test",
                "lastName", "Login",
                "phoneNumber", "987654321"
        );

        // Rejestracja
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        // Logowanie
        Map<String, String> loginData = Map.of("email", email, "password", password);

        mockMvc.perform(post("/api/users/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.accessToken").isNotEmpty());
    }
    @Test
    void givenInvalidCredentials_whenAuthenticate_thenUnauthorized() throws Exception {
        Map<String, String> loginData = Map.of(
                "email", existingEmail,
                "password", "wrongPassword"
        );

        mockMvc.perform(post("/api/users/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginData)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid email or password"));
    }

    @Test
    void givenMissingPassword_whenAuthenticate_thenBadRequest() throws Exception {
        Map<String, String> loginData = Map.of("email", existingEmail);

        mockMvc.perform(post("/api/users/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginData)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Email and password are required"));
    }

    @Test
    void givenMissingEmail_whenAuthenticate_thenBadRequest() throws Exception {
        Map<String, String> loginData = Map.of("password", password);

        mockMvc.perform(post("/api/users/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginData)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Email and password are required"));
    }
}
