package com.systemzarzadzaniaapteka.model;
import jakarta.persistence.*;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa reprezentująca dane sprzedażowe w systemie zarządzania apteką.
 * 
 * <p>Klasa SalesData przechowuje informacje o danych sprzedażowych,
 * w tym datę sprzedaży, całkowity przychód, liczbę sprzedanych przedmiotów
 * oraz szczegóły sprzedaży według leków.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class SalesData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate salesDate;

    private Double totalRevenue;

    private Integer totalItemsSold;

    //  mozna przechowywac jako JSON
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

    public SalesData() {
    }

    public SalesData(LocalDate salesDate, Double totalRevenue, Integer totalItemsSold, Map<String, Double> salesByMedicine) {
        this.salesDate = salesDate;
        this.totalRevenue = totalRevenue;
        this.totalItemsSold = totalItemsSold;
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

    public LocalDate getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(LocalDate salesDate) {
        this.salesDate = salesDate;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Integer getTotalItemsSold() {
        return totalItemsSold;
    }

    public void setTotalItemsSold(Integer totalItemsSold) {
        this.totalItemsSold = totalItemsSold;
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

    public void displaySalesSummary() {
        System.out.println("Sales Summary for " + salesDate);
        System.out.println("Total Revenue: " + totalRevenue);
        System.out.println("Total Items Sold: " + totalItemsSold);
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
    public SalesReport analyzeSales() {
        // return analysis (Facade pattern could be used for report generation)
        return new SalesReport();
    }
}
