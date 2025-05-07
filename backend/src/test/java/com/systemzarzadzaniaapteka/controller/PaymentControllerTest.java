package com.systemzarzadzaniaapteka.controller;

import com.stripe.exception.StripeException;
import com.systemzarzadzaniaapteka.dto.PaymentRequest;
import com.systemzarzadzaniaapteka.exception.PaymentProcessingException;
import com.systemzarzadzaniaapteka.model.Payment;
import com.systemzarzadzaniaapteka.model.PaymentIntent;
import com.systemzarzadzaniaapteka.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @Test
    void createPaymentIntent_Success() throws StripeException {
        // Given
        PaymentIntent intent = new PaymentIntent();
        when(paymentService.createPaymentIntent(anyLong())).thenReturn(intent);

        // When
        ResponseEntity<PaymentIntent> response = paymentController.createPaymentIntent(1L);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(intent, response.getBody());
    }


    @Test
    void createPaymentIntent_Failure() throws Exception {
        StripeException ex = mock(StripeException.class);
        when(paymentService.createPaymentIntent(anyLong())).thenThrow(ex);

        assertThrows(PaymentProcessingException.class, () -> {
            paymentController.createPaymentIntent(1L);
        });
    }

    @Test
    void processPayment() {
        // Given
        PaymentRequest request = new PaymentRequest();
        Payment payment = new Payment();
        when(paymentService.processPayment(any(PaymentRequest.class))).thenReturn(payment);

        // When
        ResponseEntity<Payment> response = paymentController.processPayment(request);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(payment, response.getBody());
    }

    @Test
    void confirmOfflinePayment_Success() {
        // Given
        Long id = 1L;
        Payment payment = new Payment();
        when(paymentService.confirmOfflinePayment(id)).thenReturn(payment);

        // When
        ResponseEntity<Payment> response = paymentController.confirmOfflinePayment(id);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(payment, response.getBody());
    }

    @Test
    void confirmOfflinePayment_NotFound() {
        // Given
        Long id = 1L;
        when(paymentService.confirmOfflinePayment(id)).thenThrow(IllegalArgumentException.class);

        // When
        ResponseEntity<Payment> response = paymentController.confirmOfflinePayment(id);

        // Then
        assertEquals(404, response.getStatusCodeValue());
    }
}