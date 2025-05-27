package com.systemzarzadzaniaapteka.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systemzarzadzaniaapteka.model.Medicine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser
public class MedicineControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void createMedicine_Success() throws Exception {
        Medicine medicine = new Medicine();
        medicine.setName("Ibuprofen");
        medicine.setPrice(15.99f);
        medicine.setQuantity(50);

        mockMvc.perform(post("/api/medicines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medicine)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void getAllMedicines_Success() throws Exception {
        mockMvc.perform(get("/api/medicines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void updateMedicine_Success() throws Exception {
        Medicine medicine = new Medicine();
        medicine.setName("Paracetamol");
        medicine.setPrice(8.99f);
        medicine.setQuantity(30);

        // First create
        String response = mockMvc.perform(post("/api/medicines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medicine)))
                .andReturn().getResponse().getContentAsString();

        Medicine created = objectMapper.readValue(response, Medicine.class);
        created.setPrice(9.99f);

        mockMvc.perform(put("/api/medicines/" + created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(9.99));
    }
}