package com.systemzarzadzaniaapteka.service;

import com.systemzarzadzaniaapteka.model.AppUser;
import com.systemzarzadzaniaapteka.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;



    @Test
    void processOAuth2User_NewUser() {
        String email = "new@example.com";
        String name = "New User";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AppUser result = userService.processOAuth2User(email, name);

        assertEquals(email, result.getEmail());
        assertEquals("CLIENT", result.getRole());
    }

    @Test
    void registerUserFromMap() {
        Map<String, String> userData = Map.of(
                "email", "test@example.com",
                "password", "password123",
                "firstName", "John",
                "lastName", "Doe",
                "phoneNumber", "123456789"
        );

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");
        when(passwordEncoder.matches(eq("password123"), eq("encodedPassword"))).thenReturn(true);
        AppUser result = userService.registerUserFromMap(userData);

        assertEquals("John Doe", result.getName());
        assertTrue(passwordEncoder.matches("password123", result.getPassword()));
    }

    @Test
    void authenticate_Success() {
        String email = "user@example.com";
        String password = "password";
        AppUser user = new AppUser();

        // Zamockuj encode i ustaw haslo
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");
        user.setPassword(passwordEncoder.encode(password));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, "encodedPassword")).thenReturn(true);

        AppUser result = userService.authenticate(email, password);

        assertEquals(user, result);
    }


}