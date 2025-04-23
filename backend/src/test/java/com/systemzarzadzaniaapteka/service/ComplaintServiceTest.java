package com.systemzarzadzaniaapteka.service;

import com.systemzarzadzaniaapteka.model.Complaint;
import com.systemzarzadzaniaapteka.repository.ComplaintRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComplaintServiceTest {

    @Mock
    private ComplaintRepository complaintRepository;

    @InjectMocks
    private ComplaintService complaintService;

    @Test
    void save() {
        Complaint complaint = new Complaint();
        when(complaintRepository.save(complaint)).thenReturn(complaint);

        Complaint result = complaintService.save(complaint);

        assertNotNull(result);
        assertEquals("OPEN", result.getStatus());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void resolve() {
        Long id = 1L;
        Complaint complaint = new Complaint();
        Complaint update = new Complaint();
        update.setStatus("RESOLVED");

        when(complaintRepository.findById(id)).thenReturn(Optional.of(complaint));
        when(complaintRepository.save(complaint)).thenReturn(complaint);

        Complaint result = complaintService.resolve(id, update);

        assertEquals("RESOLVED", result.getStatus());
        assertNotNull(result.getResolvedAt());
    }
}