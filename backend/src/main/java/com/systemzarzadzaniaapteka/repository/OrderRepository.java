package com.systemzarzadzaniaapteka.repository;
import com.systemzarzadzaniaapteka.model.OrderStatus;

import com.systemzarzadzaniaapteka.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.systemzarzadzaniaapteka.model.AppUser;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * Interfejs repozytorium dla zamówień w systemie zarządzania apteką.
 * 
 * <p>Interfejs OrderRepository rozszerza JpaRepository i zapewnia metody do zarządzania encjami Order,
 * w tym wyszukiwanie zamówień na podstawie statusu, użytkownika, daty zamówienia oraz kwoty całkowitej.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    // ewentualne dodatkowe metody zapytan
    // Przykladowa metoda: znajdz zamowienia po statusie
        List<Order> findByStatus(String status);
    
        List<Order> findByUserId(Long userId); // Dodaj te metode

        // Przykladowa metoda: znajdz zamowienia po kliencie 
        // List<Order> findByCustomer(Customer customer);

    // Znajdz zamowienia po uzytkowniku id
    List<Order> findByUser(AppUser user);

    // Znajdz zamowienia miedzy datami
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Znajdz zamowienia o kwocie wiekszej niz podana
    List<Order> findByTotalAmountGreaterThan(BigDecimal amount);

    // Znajdz zamowienia po statusie i uzytkowniku
    List<Order> findByStatusAndUser(OrderStatus status, AppUser user);
}
