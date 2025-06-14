package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.model.Complaint;
import com.systemzarzadzaniaapteka.service.ComplaintService;
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
class ComplaintControllerTest {

    @Mock
    private ComplaintService complaintService;

    @InjectMocks
    private ComplaintController complaintController;

    @Test
    void getAll() {
        // Given
        Complaint complaint1 = new Complaint();
        Complaint complaint2 = new Complaint();
        List<Complaint> complaints = Arrays.asList(complaint1, complaint2);
        when(complaintService.getAll()).thenReturn(complaints);

        // When
        List<Complaint> result = complaintController.getAll();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void getById() {
        // Given
        Long id = 1L;
        Complaint complaint = new Complaint();
        when(complaintService.getById(id)).thenReturn(complaint);

        // When
        Complaint result = complaintController.getById(id);

        // Then
        assertEquals(complaint, result);
    }

    @Test
    void create() {
        // Given
        Complaint complaint = new Complaint();
        when(complaintService.save(any(Complaint.class))).thenReturn(complaint);

        // When
        Complaint result = complaintController.create(complaint);

        // Then
        assertEquals(complaint, result);
    }

    @Test
    void resolve() {
        // Given
        Long id = 1L;
        Complaint complaint = new Complaint();
        when(complaintService.resolve(eq(id), any(Complaint.class))).thenReturn(complaint);

        // When
        Complaint result = complaintController.resolve(id, complaint);

        // Then
        assertEquals(complaint, result);
    }

    @Test
    void delete() {
        // Given
        Long id = 1L;

        // When
        complaintController.delete(id);

        // Then
        verify(complaintService).delete(id);
    }
}