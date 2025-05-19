
package com.systemzarzadzaniaapteka.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca sprawdzenie stanu magazynu w systemie zarządzania apteką.
 * 
 * <p>Klasa StockCheck przechowuje informacje o przeprowadzonym sprawdzeniu stanu magazynu,
 * w tym datę sprawdzenia, listę sprawdzonych leków oraz osobę odpowiedzialną za sprawdzenie.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class StockCheck {
    private static StockCheck instance = null;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime checkDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "stock_check_id") // FK w tabeli medicine
    private List<Medicine> checkedMedicines = new ArrayList<>();

    private String checkedBy; // Mozesz tu wstawic np. nazwe pracownika lub powiazac z encja User

    public StockCheck() {
    }

    public StockCheck(LocalDateTime checkDate, List<Medicine> checkedMedicines, String checkedBy) {
        this.checkDate = checkDate;
        this.checkedMedicines = checkedMedicines;
        this.checkedBy = checkedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(LocalDateTime checkDate) {
        this.checkDate = checkDate;
    }

    public List<Medicine> getCheckedMedicines() {
        return checkedMedicines;
    }

    public void setCheckedMedicines(List<Medicine> checkedMedicines) {
        this.checkedMedicines = checkedMedicines;
    }

    public String getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(String checkedBy) {
        this.checkedBy = checkedBy;
    }

    public void performCheck() {
        System.out.println("Stock check performed by: " + checkedBy + " on " + checkDate);
        if (checkedMedicines != null) {
            for (Medicine medicine : checkedMedicines) {
                System.out.println(" - " + medicine.getName() + ": " + medicine.getQuantity());
            }
        }
    }
    public static StockCheck getInstance() {
        if (instance == null) {
            instance = new StockCheck(LocalDateTime.now(), new ArrayList<>(), "System");
        }
        return instance;
    }

}
