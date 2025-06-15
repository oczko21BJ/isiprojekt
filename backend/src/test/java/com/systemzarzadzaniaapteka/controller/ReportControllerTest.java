package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.model.Report;
import com.systemzarzadzaniaapteka.model.ReportRequest;
import com.systemzarzadzaniaapteka.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    @Test
    void getAllReports() {
        // Given
        Report report1 = new Report();
        Report report2 = new Report();
        when(reportService.getAll()).thenReturn(Arrays.asList(report1, report2));

        // When
        List<Report> result = reportController.getAllReports();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void getById() {
        // Given
        Long id = 1L;
        Report report = new Report();
        when(reportService.getById(id)).thenReturn(report);

        // When
        Report result = reportController.getById(id);

        // Then
        assertEquals(report, result);
    }

    @Test
    void generate() {
        // Given
        ReportRequest request = new ReportRequest();
        Report report = new Report();
        when(reportService.generate(any(ReportRequest.class))).thenReturn(report);

        // When
        Report result = reportController.generate(request);

        // Then
        assertEquals(report, result);
    }
}