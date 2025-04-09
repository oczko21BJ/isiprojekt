package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.model.Report;
import com.systemzarzadzaniaapteka.model.ReportRequest;
import com.systemzarzadzaniaapteka.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Klasa kontrolera do zarządzania raportami w systemie zarządzania apteką.
 * 
 * <p>Klasa ReportController zapewnia endpointy do obsługi raportów,
 * w tym pobieranie wszystkich raportów, pobieranie raportu po ID
 * oraz generowanie nowego raportu na podstawie żądania.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public List<Report> getAllReports() {
        return reportService.getAll();
    }

    @GetMapping("/{id}")
    public Report getById(@PathVariable Long id) {
        return reportService.getById(id);
    }

    @PostMapping
    public Report generate(@RequestBody ReportRequest request) {
        return reportService.generate(request);
    }
}
