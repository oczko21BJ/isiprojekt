package com.systemzarzadzaniaapteka.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca właściciela apteki w systemie zarządzania apteką.
 * 
 * <p>Klasa Owner dziedziczy po AppUser i reprezentuje użytkownika
 * z uprawnieniami właściciela, umożliwiającymi zarządzanie personelem,
 * monitorowanie stanu magazynu oraz dostęp do raportów finansowych.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Owner extends AppUser {
   private String pharmacyName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppUser> staffMembers = new ArrayList<>();

    public Owner() {
        super();
    }

    public Owner( String name, String email, String phone, String role, String password) {
        super(name, email, phone, "Owner", password);
    }

    public List<AppUser> getStaffMembers() {
        return staffMembers;
    }

    public void setStaffMembers(List<AppUser> staffMembers) {
        this.staffMembers = staffMembers;
    }

    public void addStaffMember(AppUser user) {
        staffMembers.add(user);
        System.out.println("Staff member added: " + user.getName());
    }

    public void removeStaffMember(AppUser user) {
        staffMembers.remove(user);
        System.out.println("Staff member removed: " + user.getName());
    }

    public void viewPharmacyStatus() {
        System.out.println("Pharmacy: " + pharmacyName + " | Staff count: " + staffMembers.size());
    }

    public void accessFinancialReports() {
        System.out.println("Accessing financial reports...");
    }

    public void monitorInventory() {
        System.out.println("Monitoring inventory...");
    }

    public void managePersonnel() {
        System.out.println("Managing pharmacy personnel...");
    }
}
