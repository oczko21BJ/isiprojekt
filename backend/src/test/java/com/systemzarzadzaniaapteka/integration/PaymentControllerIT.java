package com.systemzarzadzaniaapteka.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.net.StripeResponse;
import com.systemzarzadzaniaapteka.dto.PaymentRequest;
import com.systemzarzadzaniaapteka.model.Order;
import com.systemzarzadzaniaapteka.model.OrderStatus;
import com.systemzarzadzaniaapteka.model.Payment;
import com.systemzarzadzaniaapteka.model.PaymentStatus;
import com.systemzarzadzaniaapteka.repository.OrderRepository;
import com.systemzarzadzaniaapteka.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class PaymentControllerIT {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private OrderRepository orderRepository;
    @Autowired private PaymentRepository paymentRepository;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    private Order testOrder;
    private Payment testPayment;

    @BeforeEach
    void setUp() throws IllegalAccessException {
        // Konfiguracja Stripe
        Stripe.apiKey = stripeSecretKey;

        // Tworzenie testowego zamówienia
        testOrder = new Order();
        testOrder.setStatus(OrderStatus.PENDING);
        testOrder.setOrderDate(LocalDateTime.now());
        testOrder.setTotalAmount(100.0);
        orderRepository.save(testOrder);

        // Tworzenie testowej płatności
        testPayment = new Payment();
        testPayment.setOrder(testOrder);
        testPayment.setAmount(BigDecimal.valueOf(100.0));
        testPayment.setStatus(PaymentStatus.PENDING);
        paymentRepository.save(testPayment);
    }

    @Test
    void createPaymentIntent_Success() throws Exception {
        mockMvc.perform(post("/api/payments/create-intent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrder.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.clientSecret").isNotEmpty());
    }

    @Test
    void processPayment_Stripe_Success() throws Exception {
        PaymentRequest request = new PaymentRequest();
        request.setOrderId(testOrder.getId());
        request.setPaymentMethod("stripe");
        request.setAmount(100.0f);
        request.setCardNumber("4242424242424242");
        request.setCardHolderName("Test User");
        request.setExpiryDate("12/30");
        request.setCvv("123");

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void processPayment_Offline_Success() throws Exception {
        PaymentRequest request = new PaymentRequest();
        request.setOrderId(testOrder.getId());
        request.setPaymentMethod("cash");
        request.setAmount(100.0f);

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void confirmOfflinePayment_Success() throws Exception {
        // Aktualizacja statusu płatności
        testPayment.setStatus(PaymentStatus.PENDING);
        paymentRepository.save(testPayment);

        mockMvc.perform(put("/api/payments/" + testPayment.getId() + "/confirm-offline"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void getPaymentStatus_Success() throws Exception {
        // Aktualizacja statusu płatności
        testPayment.setStatus(PaymentStatus.COMPLETED);
        testPayment.setPaymentDate(LocalDateTime.now());
        testPayment.setTransactionId("txn_123456");
        paymentRepository.save(testPayment);

        // Aktualizacja statusu zamówienia
        testOrder.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(testOrder);

        mockMvc.perform(get("/api/payments/status/" + testOrder.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentExists").value(true))
                .andExpect(jsonPath("$.paymentStatus").value("COMPLETED"))
                .andExpect(jsonPath("$.orderStatus").value("COMPLETED"));
    }
}
