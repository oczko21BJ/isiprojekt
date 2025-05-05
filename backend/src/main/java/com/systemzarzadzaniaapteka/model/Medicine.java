package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Klasa reprezentująca lek w systemie zarządzania apteką.
 * 
 * <p>Encja Medicine przechowuje wszystkie informacje dotyczące leków dostępnych w aptece,
 * w tym dane o cenie, ilości, producencie oraz relacje z dostawcami i hurtownikami.</p>
 * 
 * <p>Klasa obsługuje:</p>
 * <ul>
 *   <li>Zarządzanie stanem magazynowym</li>
 *   <li>Kontrolę minimalnego poziomu zapasów</li>
 *   <li>Relacje z zamówieniami i receptami</li>
 *   <li>Powiązania z dostawcami i hurtownikami</li>
 * </ul>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Medicine {
    /** Unikalny identyfikator leku w bazie danych */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nazwa leku */
    public String name;
    
    /** Cena leku w złotych */
    public Float price;
    
    /** Aktualna ilość leku w magazynie */
    private Integer quantity;
    
    /** Minimalny poziom zapasów, poniżej którego należy uzupełnić stan */
    private Integer minimumStockLevel;
    
    /** Nazwa producenta leku */
    private String manufacturer;
    
    /** Opis leku i jego właściwości */
    private String description;
    
    /** Lista pozycji zamówień zawierających ten lek */
    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    
    /** Dostawca leku */
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    /** Lista recept elektronicznych zawierających ten lek */
    @ManyToMany(mappedBy = "medicines")
    private List<EPrescription> prescriptions;
    
    /** Hurtownik dostarczający dany lek */
    @ManyToOne
    @JoinColumn(name = "wholesaler_id")
    private Wholesaler wholesaler;

    /**
     * Domyślny konstruktor wymagany przez JPA.
     */
    public Medicine() {}

    /**
     * Konstruktor tworzący nowy lek z podstawowymi informacjami.
     * 
     * @param name nazwa leku
     * @param price cena leku w złotych
     * @param quantity ilość leku w magazynie
     * @param minimumStockLevel minimalny poziom zapasów
     * @param manufacturer nazwa producenta
     * @param description opis leku
     */
    public Medicine(String name, Float price, Integer quantity, Integer minimumStockLevel, String manufacturer,  String description) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.minimumStockLevel = minimumStockLevel;
        this.manufacturer = manufacturer;
        this.description = description;
    }

    // Gettery i settery
    
    /** @return unikalny identyfikator leku */
    public Long getId() { return id; }
    
    /** @param id unikalny identyfikator leku */
    public void setId(Long id) { this.id = id; }
    
    /** @return nazwa leku */
    public String getName() { return name; }
    
    /** @param name nazwa leku */
    public void setName(String name) { this.name = name; }
    
    /** @return cena leku w złotych */
    public Float getPrice() { return price; }
    
    /** @param price cena leku w złotych */
    public void setPrice(Float price) { this.price = price; }
    
    /** @return aktualna ilość leku w magazynie */
    public Integer getQuantity() { return quantity; }
    
    /** @param quantity aktualna ilość leku w magazynie */
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    /** @return minimalny poziom zapasów */
    public Integer getMinimumStockLevel() { return minimumStockLevel; }
    
    /** @param minimumStockLevel minimalny poziom zapasów */
    public void setMinimumStockLevel(Integer minimumStockLevel) { this.minimumStockLevel = minimumStockLevel; }
    
    /** @return nazwa producenta leku */
    public String getManufacturer() { return manufacturer; }
    
    /** @param manufacturer nazwa producenta leku */
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    
    /** @return opis leku i jego właściwości */
    public String getDescription() {return description;}
    
    /** @param description opis leku i jego właściwości */
    public void setDescription(String description) {this.description = description;}
    
    /** @return dostawca leku */
    public Supplier getSupplier() { return supplier; }
    
    /** @param supplier dostawca leku */
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }

    /**
     * Sprawdza czy stan magazynowy leku jest na poziomie krytycznym.
     * 
     * @return true jeśli ilość leku jest mniejsza lub równa minimalnemu poziomowi zapasów, false w przeciwnym razie
     */
    public boolean checkStock() {
        return quantity <= minimumStockLevel;
    }

    /**
     * Aktualizuje ilość leku w magazynie.
     * 
     * @param newQuantity nowa ilość leku w magazynie
     */
    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    /**
     * Zmienia cenę leku.
     * 
     * @param newPrice nowa cena leku w złotych
     */
    public void changePrice(float newPrice) {
        this.price = newPrice;
    }

    /**
     * Zwraca tekstową reprezentację leku zawierającą nazwę, cenę i ilość.
     * 
     * @return sformatowany ciąg znaków z podstawowymi informacjami o leku
     */
    @Override
    public String toString() {
        return name + " (Price: " + price + ", Qty: " + quantity + ")";
    }
}
