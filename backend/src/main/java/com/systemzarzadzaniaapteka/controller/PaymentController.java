package com.systemzarzadzaniaapteka.controller;

import com.stripe.exception.StripeException;
import com.systemzarzadzaniaapteka.model.Payment;
import com.systemzarzadzaniaapteka.model.PaymentIntent;
import com.systemzarzadzaniaapteka.service.PaymentService;
import com.systemzarzadzaniaapteka.exception.PaymentProcessingException;
import com.systemzarzadzaniaapteka.dto.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Klasa kontrolera do zarządzania płatnościami w systemie zarządzania apteką.
 * 
 * <p>Klasa PaymentController zapewnia endpointy do obsługi płatności,
 * w tym tworzenie intencji płatności, przetwarzanie płatności
 * oraz potwierdzanie płatności offline.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-intent")
    @PreAuthorize("hasRole('ROLE_CLIENT') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<PaymentIntent> createPaymentIntent(@RequestBody Long orderId) {
        try {
            PaymentIntent intent = paymentService.createPaymentIntent(orderId);
            return ResponseEntity.ok(intent);
        } catch (StripeException e) {
            throw new PaymentProcessingException("Blad przetwarzania platnosci");
        }
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_CLIENT') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Payment> processPayment(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.processPayment(request));
    }

    @PutMapping("/{id}/confirm-offline")
    public ResponseEntity<Payment> confirmOfflinePayment(@PathVariable Long id) {
        try {
            Payment payment = paymentService.confirmOfflinePayment(id);
            return ResponseEntity.ok(payment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
