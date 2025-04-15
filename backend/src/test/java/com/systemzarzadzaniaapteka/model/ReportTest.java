package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReportTest {

    @Test
    void shouldStoreReportData() {
        Report report = new Report();
        report.setContent("Szczegoly audytu");

        assertEquals("Szczegoly audytu", report.getContent());
    }
}
