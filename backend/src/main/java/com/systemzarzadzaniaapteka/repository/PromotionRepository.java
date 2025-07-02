package com.systemzarzadzaniaapteka.repository;

import com.systemzarzadzaniaapteka.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfejs repozytorium dla promocji w systemie zarządzania apteką.
 * 
 * <p>Interfejs PromotionRepository rozszerza JpaRepository i zapewnia metody do zarządzania encjami Promotion.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
