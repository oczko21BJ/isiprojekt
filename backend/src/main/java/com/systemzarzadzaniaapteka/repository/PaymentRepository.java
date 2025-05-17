package com.systemzarzadzaniaapteka.repository;

import com.systemzarzadzaniaapteka.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.systemzarzadzaniaapteka.model.Order;
import com.systemzarzadzaniaapteka.model.PaymentStatus;

import java.util.List;

/**
 * Interfejs repozytorium dla płatności w systemie zarządzania apteką.
 * 
 * <p>Interfejs PaymentRepository rozszerza JpaRepository i zapewnia metody do zarządzania encjami Payment,
 * w tym wyszukiwanie płatności na podstawie ID zamówienia, statusu płatności oraz ID Stripe Payment Intent.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
       // Przyklad: znajdz platnosci po statusie
    List<Payment> findByOrderId(Long orderId);
    List<Payment> findByOrder(Order order);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByStripePaymentIntentId(String stripePaymentIntentId);



    // Przyklad: znajdz platnosci po zamowieniu (jesli Payment ma pole order)
    // List<Payment> findByOrder(Order order);
    
}
