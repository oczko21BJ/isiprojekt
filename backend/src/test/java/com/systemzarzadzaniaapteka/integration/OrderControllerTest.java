package com.systemzarzadzaniaapteka.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systemzarzadzaniaapteka.dto.OrderDto;
import com.systemzarzadzaniaapteka.dto.OrderItemDto;
import com.systemzarzadzaniaapteka.model.AppUser;
import com.systemzarzadzaniaapteka.model.Medicine;
import com.systemzarzadzaniaapteka.repository.MedicineRepository;
import com.systemzarzadzaniaapteka.repository.UserRepository;
import com.systemzarzadzaniaapteka.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserRepository userRepository;
    @Autowired private MedicineRepository medicineRepository;
    @Autowired private JwtTokenProvider tokenProvider;

    private AppUser testUser;
    private Medicine testMedicine;
    private String jwtToken;

    @BeforeEach
    void setUp() {
        testUser = new AppUser();
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");
        testUser.setRole("CUSTOMER");
        userRepository.save(testUser);

        testMedicine = new Medicine();
        testMedicine.setName("Aspirin");
        testMedicine.setPrice(10.0f);
        testMedicine.setQuantity(100);
        medicineRepository.save(testMedicine);

        jwtToken = tokenProvider.generateToken(testUser);
    }

    private RequestPostProcessor jwtAuth() {
        return request -> {
            request.addHeader("Authorization", "Bearer " + jwtToken);
            return request;
        };
    }

    @Test
    void createOrder_Success() throws Exception {
        OrderItemDto itemDto = new OrderItemDto();
        itemDto.setMedicineId(testMedicine.getId());
        itemDto.setQuantity(2);
        itemDto.setPrice(10.0f);

        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(testUser.getId());
        orderDto.setItems(List.of(itemDto));
        orderDto.setSubtotal(20.0f);
        orderDto.setTax(2.0f);
        orderDto.setDeliveryFee(5.0f);
        orderDto.setTotal(27.0f);
        orderDto.setDeliveryAddress("123 Main St");
        orderDto.setSpecialInstructions("Ring the bell");

        mockMvc.perform(post("/api/orders")
                        .with(jwtAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void getAllOrders_Success() throws Exception {
        mockMvc.perform(get("/api/orders")
                        .with(jwtAuth()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}