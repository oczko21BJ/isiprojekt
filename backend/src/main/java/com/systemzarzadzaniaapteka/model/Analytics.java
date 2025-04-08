package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa reprezentująca dane analityczne w systemie zarządzania apteką.
 * 
 * <p>Klasa Analytics przechowuje dane analityczne w formacie JSON oraz jako mapa,
 * umożliwiając generowanie raportów i wniosków na podstawie zebranych informacji.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Analytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String dataJson;

    @Transient
    private Map<String, Object> data = new HashMap<>();

    public Analytics() {
    }

    public Analytics(Map<String, Object> data) {
        this.data = data;
       
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
       
    }

    public void generateInsights() {
        System.out.println("Generating analytics insights.");
    }

 
}
