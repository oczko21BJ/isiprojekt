package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.*;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa reprezentująca zakres dat w systemie zarządzania apteką.
 * 
 * <p>Klasa DateRange przechowuje informacje o zakresie dat,
 * w tym datę początkową i końcową.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
class DateRange {
    private LocalDate startDate;
    private LocalDate endDate;

    public DateRange() {}

    public DateRange(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
/**
 * Klasa reprezentująca raport sprzedaży w systemie zarządzania apteką.
 * 
 * <p>Klasa SalesReport przechowuje informacje o raporcie sprzedaży,
 * w tym datę raportu, całkowity przychód, liczbę sprzedaży
 * oraz szczegóły sprzedaży według leków.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class SalesReport {
    //private int id;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private LocalDate reportDate;
    private Double totalRevenue;
    private Integer totalSales;

    // mozna przechowywac jako JSON
    @Lob
    private String detailsJson;

    @ElementCollection
    @CollectionTable(
            name = "customer_registration_details",
            joinColumns = @JoinColumn(name = "registration_id")
    )
    @MapKeyColumn(name = "detail_key")
    @Column(name = "detail_value")
    private Map<String, Double> salesByMedicine = new HashMap<>();

    public SalesReport() {
    }

    public SalesReport(LocalDate reportDate, Double totalRevenue, Integer totalSales, Map<String, Double> salesByMedicine) {
        this.reportDate = reportDate;
        this.totalRevenue = totalRevenue;
        this.totalSales = totalSales;
        this.salesByMedicine = salesByMedicine;
        // Mozesz tu dodac serializacje mapy do JSON-a, jesli chcesz przechowywac ja w bazie
        // this.detailsJson = serializeToJson(salesByMedicine);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

    public String getDetailsJson() {
        return detailsJson;
    }

    public void setDetailsJson(String detailsJson) {
        this.detailsJson = detailsJson;
    }

    public Map<String, Double> getSalesByMedicine() {
        return salesByMedicine;
    }

    public void setSalesByMedicine(Map<String, Double> salesByMedicine) {
        this.salesByMedicine = salesByMedicine;
        // Mozesz tu dodac serializacje mapy do JSON-a, jesli chcesz przechowywac ja w bazie
        // this.detailsJson = serializeToJson(salesByMedicine);
    }


    public void generateReport(Map<String, String> filters) {
        // Strategy pattern: apply filters in different ways
        System.out.println("Generating sales report with filters: " + filters);
    }

     public void displayReport() {
        System.out.println("Sales Report for " + reportDate);
        System.out.println("Total Revenue: " + totalRevenue);
        System.out.println("Total Sales: " + totalSales);
        if (salesByMedicine != null && !salesByMedicine.isEmpty()) {
            System.out.println("Sales by Medicine:");
            for (Map.Entry<String, Double> entry : salesByMedicine.entrySet()) {
                System.out.println(" - " + entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    // Jesli chcesz serializowac mape do JSON-a, mozesz uzyc biblioteki Jackson:
    /*
    private String serializeToJson(Map<String, Double> map) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(map);
        } catch (Exception e) {
            return "{}";
        }
    }
    */
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
