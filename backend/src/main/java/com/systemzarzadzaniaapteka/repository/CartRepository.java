package com.systemzarzadzaniaapteka.repository;

import com.systemzarzadzaniaapteka.model.Cart;
import com.systemzarzadzaniaapteka.model.AppUser;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfejs repozytorium dla koszyków w systemie zarządzania apteką.
 * 
 * <p>Interfejs CartRepository rozszerza JpaRepository i zapewnia metody do zarządzania encjami Cart,
 * w tym wyszukiwanie koszyka na podstawie użytkownika.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(AppUser user);
}
