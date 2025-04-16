package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.model.AppUser;
import com.systemzarzadzaniaapteka.model.Cart;
import com.systemzarzadzaniaapteka.security.CustomOidcUser;
import com.systemzarzadzaniaapteka.service.CartService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    private CustomOidcUser createTestUser() {
        AppUser appUser = new AppUser();
        appUser.setId(1L);
        appUser.setName("Test User");
        appUser.setEmail("test@example.com");
        appUser.setRole("USER");
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "123");
        claims.put("name", "Test User");
        OidcIdToken idToken = new OidcIdToken("token", Instant.now(), Instant.now().plusSeconds(3600), claims);
        OidcUserInfo userInfo = new OidcUserInfo(claims);
        
        return new CustomOidcUser(
            appUser,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
            idToken,
            userInfo
        );
    }

    @Test
    void getCart_Unauthenticated() {
        // When
        ResponseEntity<?> response = cartController.getCart(null);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User not authenticated", response.getBody());
    }

    @Test
    void getCart_Authenticated() {
        // Given
        CustomOidcUser user = createTestUser();
        Cart expectedCart = new Cart();
        when(cartService.getOrCreateCart(user.getAppUser().getId())).thenReturn(expectedCart);

        // When
        ResponseEntity<?> response = cartController.getCart(user);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCart, response.getBody());
    }

    @Test
    void addItem() {
        // Given
        CustomOidcUser user = createTestUser();
        Cart expectedCart = new Cart();
        when(cartService.addItemToCart(eq(user.getAppUser()), anyLong(), anyInt())).thenReturn(expectedCart);

        // When
        Cart result = cartController.addItem(user, 1L, 2);

        // Then
        assertEquals(expectedCart, result);
    }

    @Test
    void clearCart() {
        // Given
        CustomOidcUser user = createTestUser();

        // When
        cartController.clearCart(user);

        // Then
        verify(cartService).clearCart(user.getAppUser().getId());
    }
}