package com.systemzarzadzaniaapteka.service;

import com.systemzarzadzaniaapteka.model.Complaint;
import com.systemzarzadzaniaapteka.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Klasa serwisowa do zarządzania reklamacjami w systemie zarządzania apteką.
 * 
 * <p>Klasa ComplaintService zapewnia metody do obsługi reklamacji,
 * w tym pobieranie wszystkich reklamacji, pobieranie reklamacji po ID,
 * zapisywanie nowych reklamacji, rozwiązywanie istniejących reklamacji
 * oraz usuwanie reklamacji.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    @Autowired
    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public List<Complaint> getAll() {
        return complaintRepository.findAll();
    }

    public Complaint getById(Long id) {
        return complaintRepository.findById(id).orElse(null);
    }

    public Complaint save(Complaint complaint) {
        complaint.setCreatedAt(LocalDateTime.now());
        complaint.setStatus("OPEN");
        return complaintRepository.save(complaint);
    }

    public Complaint resolve(Long id, Complaint update) {
        Complaint complaint = complaintRepository.findById(id).orElse(null);
        if (complaint != null) {
            complaint.setStatus(update.getStatus() != null ? update.getStatus() : "RESOLVED");
            complaint.setResolvedAt(LocalDateTime.now());
            // Mozesz dodac inne pola aktualizowane podczas rozwiazywania
            return complaintRepository.save(complaint);
        }
        return null;
    }

    public void delete(Long id) {
        complaintRepository.deleteById(id);
    }
}
