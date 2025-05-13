package com.systemzarzadzaniaapteka.model;
import java.util.List;
//import java.util.function.Supplier;
//import com.systemzarzadzaniaapteka.model.Supplier;
import jakarta.persistence.*;

/**
 * Klasa reprezentująca farmaceutę w systemie zarządzania apteką.
 * 
 * <p>Klasa Pharmacist dziedziczy po AppUser i reprezentuje użytkownika
 * z uprawnieniami farmaceuty, umożliwiającymi zarządzanie receptami,
 * zamawianie leków oraz sprawdzanie stanu magazynu.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Pharmacist extends AppUser {
   // private String certification;
    @ManyToMany
    @JoinTable(
            name = "pharmacist_medicines", // Tabela laczaca farmaceutow z lekami
            joinColumns = @JoinColumn(name = "pharmacist_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    private List<Medicine> prescriptionsToFill;
    @ManyToOne
    @JoinColumn(name = "supplier_id") // Zakladamy, ze dostawca jest przypisany do farmaceuty
    private Supplier supplier;


    public Pharmacist() {
        super();
    }
    public Pharmacist(String name, String email, String phone, String role, String password) {
        super(name, email, phone, "Pharmacist", password);
    }

    public void orderMedicines(List<Medicine> meds, Supplier supplier) {
        // Command pattern could encapsulate order action
        System.out.println("Ordering medicines from supplier: " + supplier.getName());
    }

    public void processOrder(OnlineOrder order) {
        // Process the given order
        System.out.println("Processing order id: " + order.getId());
    }


    public void checkInventory() {
        // Delegates to InventoryCheck (Singleton pattern for check)
        InventoryCheck.getInstance().updateInventory();
        System.out.println("Inventory checked by pharmacist.");
    }

    public void sendReminder(Reminder reminder) {
        // Observer pattern: notify a user via reminder
        reminder.sendReminder();
    }
    public void verifyPrescription(EPrescription prescription) {
        if (prescription != null && prescription.validateCode()) {
            System.out.println("Prescription " + prescription + " verified by pharmacist: " + getName());
        } else {
            System.out.println("Prescription verification failed by pharmacist: " + getName());
        }
    }

    public void reportComplianceIssue(String issue) {
        ComplianceReport report = new ComplianceReport(issue, this);
        report.submitReport();
    }
}
