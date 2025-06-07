package com.systemzarzadzaniaapteka.service;

import com.systemzarzadzaniaapteka.model.Report;
import com.systemzarzadzaniaapteka.model.ReportRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Klasa serwisowa do zarządzania raportami w systemie zarządzania apteką.
 * 
 * <p>Klasa ReportService zapewnia metody do obsługi raportów,
 * w tym pobieranie wszystkich raportów, pobieranie raportu po ID
 * oraz generowanie nowego raportu na podstawie żądania.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Service
public class ReportService {

    private final List<Report> reports = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<Report> getAll() {
        return reports;
    }

    public Report getById(Long id) {
        return reports.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
    }

    public Report generate(ReportRequest request) {
        // Przykladowa logika generowania raportu
        String content = "Raport typu: " + request.getType() + " z parametrami: " + request.getParameters();
        Report report = new Report(
                counter.getAndIncrement(),
                request.getType(),
                content,
                LocalDateTime.now()
        );
        reports.add(report);
        return report;
    }
}
