package com.systemzarzadzaniaapteka.model;

/**
 * Klasa reprezentująca żądanie raportu w systemie zarządzania apteką.
 * 
 * <p>Klasa ReportRequest przechowuje informacje o żądaniu raportu,
 * w tym typ raportu oraz parametry żądania.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
public class ReportRequest {
    private String type;
    private String parameters; // Mozesz rozbudowac na Map<String, String> jesli potrzeba

    public ReportRequest() {}

    public ReportRequest(String type, String parameters) {
        this.type = type;
        this.parameters = parameters;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getParameters() { return parameters; }
    public void setParameters(String parameters) { this.parameters = parameters; }
}
