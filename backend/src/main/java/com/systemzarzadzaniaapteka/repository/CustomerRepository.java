package com.systemzarzadzaniaapteka.repository;

import com.systemzarzadzaniaapteka.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfejs repozytorium dla klientów w systemie zarządzania apteką.
 * 
 * <p>Interfejs CustomerRepository rozszerza JpaRepository i zapewnia metody do zarządzania encjami Customer,
 * w tym sprawdzanie istnienia klienta na podstawie adresu e-mail.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Mozesz dodac dodatkowe metody, np. wyszukiwanie po emailu:
    boolean existsByEmail(String email);
}
