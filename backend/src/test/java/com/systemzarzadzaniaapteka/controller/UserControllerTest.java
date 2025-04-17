package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.model.AppUser;
import com.systemzarzadzaniaapteka.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void registerUser_Success() {
        // Given
        AppUser user = new AppUser();
        when(userService.registerUserFromMap(anyMap())).thenReturn(user);
        Map<String, String> userData = Map.of("email", "test@example.com", "password", "password");

        // When
        ResponseEntity<?> response = userController.registerUser(userData);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(((Map) response.getBody()).containsKey("success"));
        assertTrue((Boolean) ((Map) response.getBody()).get("success"));
    }

    @Test
    void registerUser_Conflict() {
        // Given
        when(userService.registerUserFromMap(anyMap()))
                .thenThrow(new IllegalStateException("User already exists"));
        Map<String, String> userData = Map.of("email", "test@example.com", "password", "password");

        // When
        ResponseEntity<?> response = userController.registerUser(userData);

        // Then
        assertEquals(409, response.getStatusCodeValue());
        assertFalse((Boolean) ((Map) response.getBody()).get("success"));
    }

    @Test
    void getAllUsers() {
        // Given
        AppUser user1 = new AppUser();
        AppUser user2 = new AppUser();
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        // When
        List<AppUser> result = userController.getAllUsers();

        // Then
        assertEquals(2, result.size());
    }
}