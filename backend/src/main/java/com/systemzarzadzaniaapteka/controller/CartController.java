package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.model.Cart;
import com.systemzarzadzaniaapteka.model.AppUser;
import com.systemzarzadzaniaapteka.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.systemzarzadzaniaapteka.security.CustomOidcUser;
/**
 * Klasa kontrolera zarządzająca koszykiem użytkownika.
 * Umożliwia pobieranie, dodawanie elementów do koszyka oraz czyszczenie koszyka.
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    /**
     * Konstruktor klasy CartController.
     *
     * @param cartService usługa odpowiedzialna za zarządzanie koszykami.
     */
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    /**
     * Endpoint do pobierania koszyka użytkownika.
     *
     * Sprawdza, czy użytkownik jest zalogowany i jeśli tak, zwraca jego koszyk.
     * Jeśli użytkownik nie jest zalogowany, zwraca odpowiedź o błędzie 401.
     *
     * @param customUser obiekt reprezentujący aktualnie zalogowanego użytkownika.
     * @return odpowiedź HTTP zawierająca koszyk użytkownika lub błąd 401, jeśli użytkownik nie jest zalogowany.
     */
    @GetMapping
    public ResponseEntity<?> getCart(@AuthenticationPrincipal CustomOidcUser customUser) {
        if (customUser == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }
        AppUser user = customUser.getAppUser();
        return ResponseEntity.ok(cartService.getOrCreateCart(user.getId()));
    }
    /**
     * Endpoint do dodawania elementów do koszyka.
     *
     * Pozwala dodać produkt do koszyka użytkownika, podając jego ID oraz ilość.
     *
     * @param customUser obiekt reprezentujący aktualnie zalogowanego użytkownika.
     * @param productId ID produktu, który ma zostać dodany do koszyka.
     * @param quantity liczba sztuk produktu do dodania do koszyka.
     * @return obiekt typu Cart, zawierający zaktualizowany koszyk użytkownika.
     */
    @PostMapping("/add")
    public Cart addItem(@AuthenticationPrincipal CustomOidcUser customUser,
                        @RequestParam Long productId,
                        @RequestParam int quantity) {
        AppUser user = customUser.getAppUser();
        return cartService.addItemToCart(user, productId, quantity);
    }
    /**
     * Endpoint do czyszczenia koszyka użytkownika.
     *
     * Usuwa wszystkie przedmioty z koszyka aktualnie zalogowanego użytkownika.
     *
     * @param customUser obiekt reprezentujący aktualnie zalogowanego użytkownika.
     */
    @DeleteMapping
    public void clearCart(@AuthenticationPrincipal CustomOidcUser customUser) {
        AppUser user = customUser.getAppUser();
        cartService.clearCart(user.getId());
    }}

