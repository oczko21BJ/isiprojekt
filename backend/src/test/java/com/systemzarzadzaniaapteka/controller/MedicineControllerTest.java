package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.model.Medicine;
import com.systemzarzadzaniaapteka.service.MedicineService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicineControllerTest {

    @Mock
    private MedicineService medicineService;

    @InjectMocks
    private MedicineController medicineController;

    @Test
    void getAll() {
        // Given
        Medicine medicine1 = new Medicine();
        Medicine medicine2 = new Medicine();
        when(medicineService.getAll()).thenReturn(Arrays.asList(medicine1, medicine2));

        // When
        List<Medicine> result = medicineController.getAll();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void getById() {
        // Given
        Long id = 1L;
        Medicine medicine = new Medicine();
        when(medicineService.getById(id)).thenReturn(medicine);

        // When
        Medicine result = medicineController.getById(id);

        // Then
        assertEquals(medicine, result);
    }

    @Test
    void create() {
        // Given
        Medicine medicine = new Medicine();
        when(medicineService.save(any(Medicine.class))).thenReturn(medicine);

        // When
        Medicine result = medicineController.create(medicine);

        // Then
        assertEquals(medicine, result);
    }

    @Test
    void update() {
        // Given
        Long id = 1L;
        Medicine medicine = new Medicine();
        when(medicineService.update(eq(id), any(Medicine.class))).thenReturn(medicine);

        // When
        Medicine result = medicineController.update(id, medicine);

        // Then
        assertEquals(medicine, result);
    }

    @Test
    void delete() {
        // Given
        Long id = 1L;

        // When
        medicineController.delete(id);

        // Then
        verify(medicineService).delete(id);
    }
}