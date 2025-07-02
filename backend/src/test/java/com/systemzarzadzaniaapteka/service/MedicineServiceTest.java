package com.systemzarzadzaniaapteka.service;

import com.systemzarzadzaniaapteka.model.Medicine;
import com.systemzarzadzaniaapteka.repository.MedicineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicineServiceTest {

    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private MedicineService medicineService;

    @Test
    void getAll() {
        Medicine medicine1 = new Medicine();
        Medicine medicine2 = new Medicine();
        when(medicineRepository.findAll()).thenReturn(List.of(medicine1, medicine2));

        List<Medicine> result = medicineService.getAll();

        assertEquals(2, result.size());
        verify(medicineRepository).findAll();
    }

    @Test
    void getById_Found() {
        Long id = 1L;
        Medicine medicine = new Medicine();
        when(medicineRepository.findById(id)).thenReturn(Optional.of(medicine));

        Medicine result = medicineService.getById(id);

        assertNotNull(result);
        assertEquals(medicine, result);
    }

    @Test
    void getById_NotFound() {
        Long id = 1L;
        when(medicineRepository.findById(id)).thenReturn(Optional.empty());

        Medicine result = medicineService.getById(id);

        assertNull(result);
    }

    @Test
    void save() {
        Medicine medicine = new Medicine();
        when(medicineRepository.save(medicine)).thenReturn(medicine);

        Medicine result = medicineService.save(medicine);

        assertNotNull(result);
        verify(medicineRepository).save(medicine);
    }

    @Test
    void update_Exists() {
        Long id = 1L;
        Medicine existing = new Medicine();
        Medicine details = new Medicine();
        details.setName("New Name");
        
        when(medicineRepository.findById(id)).thenReturn(Optional.of(existing));
        when(medicineRepository.save(existing)).thenReturn(existing);

        Medicine result = medicineService.update(id, details);

        assertNotNull(result);
        assertEquals("New Name", existing.getName());
        verify(medicineRepository).save(existing);
    }

    @Test
    void update_NotExists() {
        Long id = 1L;
        Medicine details = new Medicine();
        when(medicineRepository.findById(id)).thenReturn(Optional.empty());

        Medicine result = medicineService.update(id, details);

        assertNull(result);
    }

    @Test
    void delete() {
        Long id = 1L;
        medicineService.delete(id);
        verify(medicineRepository).deleteById(id);
    }
}