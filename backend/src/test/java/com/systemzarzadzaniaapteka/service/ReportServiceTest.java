package com.systemzarzadzaniaapteka.service;

import com.systemzarzadzaniaapteka.model.Report;
import com.systemzarzadzaniaapteka.model.ReportRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Test
    void generate() {
        ReportRequest request = new ReportRequest();
        request.setType("Sales");
        request.setParameters("month=January");

        Report report = reportService.generate(request);

        assertNotNull(report);
        assertTrue(report.getContent().contains("Sales"));
    }

    @Test
    void getById() {
        ReportRequest request = new ReportRequest();
        Report generated = reportService.generate(request);

        Report found = reportService.getById(generated.getId());

        assertEquals(generated, found);
    }
}