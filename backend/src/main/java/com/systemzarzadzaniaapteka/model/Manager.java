package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Klasa reprezentująca menedżera w systemie zarządzania apteką.
 * 
 * <p>Klasa Manager dziedziczy po AppUser i reprezentuje użytkownika
 * z uprawnieniami menedżerskimi, umożliwiającymi zarządzanie zespołem,
 * promocjami oraz generowanie raportów sprzedaży.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Manager extends AppUser {
    //private String department;
   
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppUser> team = new ArrayList<>();

    public Manager() {
        super();
    }

    public Manager( String name, String email, String phone, String role, String password) {
        super( name, email, phone, "Manager", password);
    }


    public List<AppUser> getTeam() {
        return team;
    }

    public void setTeam(List<AppUser> team) {
        this.team = team;
    }

    public void addEmployee(AppUser user) {
        team.add(user);
        System.out.println("Employee added: " + user.getName());
    }
    public void managePromotions(Promotion promotion) {
        System.out.println("Managing promotion: " + promotion.getName());
    }

    public void generateReports(Map<String, String> filters) {
        SalesReport report = new SalesReport();
        report.generateReport(filters);
        report.displayReport();
    }
}
