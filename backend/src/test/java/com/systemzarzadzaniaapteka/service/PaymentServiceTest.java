package com.systemzarzadzaniaapteka.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.systemzarzadzaniaapteka.dto.PaymentRequest;
import com.systemzarzadzaniaapteka.model.*;
import com.systemzarzadzaniaapteka.repository.OrderRepository;
import com.systemzarzadzaniaapteka.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(paymentService, "stripeSecretKey", "test_key");
    }

    @Test
    void createPaymentIntent() throws StripeException {
        Long orderId = 1L;
        Order order = new Order();
        order.setTotalAmount(100.0f);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(paymentRepository.save(any(Payment.class))).thenReturn(new Payment());

        // MOCKUJEMY statyczne PaymentIntent.create()
        try (MockedStatic<PaymentIntent> paymentIntentMockedStatic = mockStatic(PaymentIntent.class)) {
            PaymentIntent stripeIntent = mock(PaymentIntent.class);
            when(stripeIntent.getId()).thenReturn("pi_test");
            when(stripeIntent.getClientSecret()).thenReturn("secret_test");
            when(stripeIntent.getAmount()).thenReturn(10000L);
            when(stripeIntent.getCurrency()).thenReturn("pln");
            when(stripeIntent.getStatus()).thenReturn("requires_payment_method");
            paymentIntentMockedStatic.when(() -> PaymentIntent.create(any(PaymentIntentCreateParams.class)))
                    .thenReturn(stripeIntent);

            com.systemzarzadzaniaapteka.model.PaymentIntent result =
                    paymentService.createPaymentIntent(orderId);

            assertNotNull(result);
            assertEquals("pi_test", result.getId());
            assertEquals("secret_test", result.getClientSecret());
        }
    }


    @Test
    void confirmOfflinePayment() {
        Long id = 1L;
        Payment payment = new Payment();
        Order order = new Order();
        payment.setOrder(order);
        when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment result = paymentService.confirmOfflinePayment(id);

        assertEquals(PaymentStatus.PAID, result.getStatus());
    }

    @Test
    void processPayment_Success() {
        PaymentRequest request = new PaymentRequest();
        request.setOrderId(1L);
        request.setAmount(100.0f);
        request.setCardNumber("4242424242424242");

        Order order = new Order();
        order.setId(1L);
        order.setTotalAmount(100.0f);

        when(orderService.getOrderById(1L)).thenReturn(order);
        when(paymentRepository.findByOrderId(1L)).thenReturn(Collections.emptyList());
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.processPayment(request);

        assertEquals(PaymentStatus.PAID, result.getStatus());
    }
}