package com.systemzarzadzaniaapteka.service;

import com.systemzarzadzaniaapteka.model.Cart;
import com.systemzarzadzaniaapteka.model.CartItem;
import com.systemzarzadzaniaapteka.model.Medicine;
import com.systemzarzadzaniaapteka.model.AppUser;
import com.systemzarzadzaniaapteka.repository.CartRepository;
import com.systemzarzadzaniaapteka.repository.MedicineRepository;
import com.systemzarzadzaniaapteka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Klasa serwisowa do zarządzania koszykami w systemie zarządzania apteką.
 * 
 * <p>Klasa CartService zapewnia metody do obsługi koszyków użytkowników,
 * w tym pobieranie lub tworzenie koszyka, dodawanie produktów do koszyka,
 * aktualizację ilości produktów w koszyku, usuwanie produktów z koszyka
 * oraz czyszczenie koszyka.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final MedicineRepository medicineRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartService(CartRepository cartRepository, MedicineRepository medicineRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.medicineRepository = medicineRepository;
        this.userRepository = userRepository;
    }

    /**
     * Pobiera istniejacy koszyk uzytkownika lub tworzy nowy, jesli nie istnieje.
     */
    public Cart getOrCreateCart(AppUser user) {
        Optional<Cart> cartOpt = cartRepository.findByUser(user);
        if (cartOpt.isPresent()) {
            return cartOpt.get();
        }
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }
    public Cart getOrCreateCart(Long userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Uzytkownik nie istnieje"));
        return getOrCreateCart(user);
    }
    /**
     * Dodaje produkt do koszyka uzytkownika.
     */
    @Transactional
    public Cart addItemToCart(AppUser user, Long productId, int quantity) {
        Cart cart = getOrCreateCart(user);
        Medicine medicine = medicineRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produkt nie istnieje"));

        // Sprawdz, czy produkt juz jest w koszyku
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getMedicine().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setMedicine(medicine);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }
        return cartRepository.save(cart);
    }

    /**
     * Czysci koszyk uzytkownika.
     */
   
    @Transactional
    public Cart updateCartItem(Long userId, Long itemId, Integer quantity) {
        Cart cart = getOrCreateCart(userId);
        
        cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .ifPresent(item -> {
                    if (quantity <= 0) {
                        cart.removeItem(item);
                    } else {
                        item.setQuantity(quantity);
                    }
                });
        
        return cartRepository.save(cart);
    }

    @Transactional
    public void removeItemFromCart(Long userId, Long itemId) {
        Cart cart = getOrCreateCart(userId);
        
        cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .ifPresent(cart::removeItem);
        
        cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
