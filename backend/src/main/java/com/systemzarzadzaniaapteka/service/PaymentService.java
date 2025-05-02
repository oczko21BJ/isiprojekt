package com.systemzarzadzaniaapteka.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.model.PaymentIntent;
import com.systemzarzadzaniaapteka.dto.PaymentRequest;
import com.systemzarzadzaniaapteka.model.Order;
import com.systemzarzadzaniaapteka.model.OrderStatus;
import com.systemzarzadzaniaapteka.model.Payment;
import com.systemzarzadzaniaapteka.model.PaymentStatus;
import com.systemzarzadzaniaapteka.repository.OrderRepository;
import com.systemzarzadzaniaapteka.repository.PaymentRepository;
import com.systemzarzadzaniaapteka.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.UUID;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Klasa serwisowa do zarządzania płatnościami w systemie zarządzania apteką.
 * 
 * <p>Klasa PaymentService zapewnia metody do obsługi płatności,
 * w tym tworzenie Stripe PaymentIntent dla zamówienia,
 * potwierdzanie płatności offline oraz przetwarzanie płatności.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Value("${stripe.secret.key:sk_test_dummy}")
    private String stripeSecretKey;

     @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }
    @Autowired
    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    /**
     * Tworzy Stripe PaymentIntent dla zamowienia.
     */
    public com.systemzarzadzaniaapteka.model.PaymentIntent createPaymentIntent(Long orderId) throws StripeException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Zakladamy ze cena zamowienia jest w zlotowkach, Stripe wymaga groszy
        BigDecimal amount = BigDecimal.valueOf(order.getTotalAmount()).multiply(BigDecimal.valueOf(100));
        long amountInCents = amount.longValueExact(); // rzutujemy na Long

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency("pln")
                .build();

        PaymentIntent intent = PaymentIntent.create(params);

        // Mozesz zapisac Payment w bazie, jesli chcemy sledzic status
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setStatus(PaymentStatus.PENDING);
        System.out.println("Updated order 3 status: " + order.getStatus());
        payment.setStripePaymentIntentId(intent.getId());
        paymentRepository.save(payment);

        // Zwroc wlasny DTO/obiekt z clientSecret
        return new com.systemzarzadzaniaapteka.model.PaymentIntent(
                intent.getId(),
                intent.getClientSecret(),
                intent.getAmount(),
                intent.getCurrency(),
                intent.getStatus());
    }

    /**
     * Potwierdza platnosc offline (np. gotowka, przelew).
     */
    public Payment confirmOfflinePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        payment.setStatus(PaymentStatus.PAID);

        paymentRepository.save(payment);

        // Mozesz zaktualizowac status zamowienia
        Order order = payment.getOrder();
        order.setStatus(OrderStatus.PAID);
        System.out.println("Updated order status: " + order);
        order.setStatus(com.systemzarzadzaniaapteka.model.OrderStatus.PAID);
        orderRepository.save(order);
        System.out.println("Updated order status: " + order);

        System.out.println("Updated payment status: " + payment);
        return payment;
    }
    @Transactional
    public Payment processPayment(PaymentRequest paymentRequest) {
        Order order = orderService.getOrderById(paymentRequest.getOrderId());


        // Zaokraglij kwoty do 2 miejsc po przecinku
        BigDecimal orderTotal = BigDecimal.valueOf(order.getTotalAmount()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal paymentAmount = BigDecimal.valueOf(paymentRequest.getAmount()).setScale(2, RoundingMode.HALF_UP);

        // Porownanie zaokraglonych kwot
        if (orderTotal.compareTo(paymentAmount) != 0) {
            System.out.println("orderTotal: " + orderTotal + ", paymentAmount: " + paymentAmount);
            throw new RuntimeException("Payment amount does not match order total");
        }
        // Check if payment already exists
        Payment existingPayment = paymentRepository.findByOrderId(order.getId()).stream().findFirst().orElse(null);


        if (existingPayment != null && existingPayment.getStatus() == PaymentStatus.PAID) {
            //throw new RuntimeException("Order has already been paid");
            return existingPayment;
        }

        
        Payment payment = existingPayment != null ? existingPayment : new Payment();
        payment.setOrder(order);
        payment.setAmount(BigDecimal.valueOf(paymentRequest.getAmount()).setScale(2, RoundingMode.HALF_UP));
        payment.setPaymentDate(LocalDateTime.now());
        
    
        boolean paymentSuccessful = processPaymentWithGateway(paymentRequest);
        
        if (paymentSuccessful) {
            payment.setStatus(PaymentStatus.PAID);

            payment.setTransactionId(generateTransactionId());
            orderService.updateOrderStatus(order.getId(), OrderStatus.PROCESSING);
            System.out.println("Updated order status: " + order.getStatus());
            orderService.updateOrderStatus(order.getId(), OrderStatus.PAID);
            System.out.println("Updated order status: " + order.getStatus());
            orderRepository.save(order);


        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }

        return paymentRepository.save(payment);
    }

    private boolean processPaymentWithGateway(PaymentRequest paymentRequest) {
        System.out.println("Wywolano processPaymentWithGateway");
        System.out.println("Card number: " + paymentRequest.getCardNumber());

        if (paymentRequest.getCardNumber() != null && !paymentRequest.getCardNumber().isEmpty()) {
            char lastDigit = paymentRequest.getCardNumber().charAt(paymentRequest.getCardNumber().length() - 1);
            boolean result = Character.isDigit(lastDigit) && (lastDigit - '0') % 2 == 0;
            System.out.println("Last digit: " + lastDigit + ", czy jest parzysta: " + result);
            return result;
        }

        System.out.println("Brak numeru karty lub jest pusty, domyslnie zwracam true");
        return true;
    }
    private String generateTransactionId() {
        return UUID.randomUUID().toString();
    }
}
