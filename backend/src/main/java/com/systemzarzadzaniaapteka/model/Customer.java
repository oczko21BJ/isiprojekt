package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca klienta apteki w systemie zarządzania.
 * 
 * <p>Klasa Customer dziedziczy po AppUser i reprezentuje klienta apteki
 * z dodatkowymi funkcjonalnościami specyficznymi dla klientów, takimi jak:</p>
 * <ul>
 *   <li>Posiadanie karty klienta z programem lojalnościowym</li>
 *   <li>Historia zamówień online</li>
 *   <li>Możliwość przeglądania dostępnych leków</li>
 *   <li>Składanie reklamacji dotyczących leków</li>
 *   <li>Proces rejestracji w systemie</li>
 * </ul>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Customer extends AppUser {

    /** Karta klienta z programem lojalnościowym */
    @OneToOne(cascade = CascadeType.ALL)
    private CustomerCard customerCard;

    /** Lista zamówień online złożonych przez klienta */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OnlineOrder> orders = new ArrayList<>();

    /**
     * Domyślny konstruktor wymagany przez JPA.
     */
    public Customer() {
        super();
    }

    /**
     * Konstruktor tworzący nowego klienta z podstawowymi danymi.
     * 
     * @param name imię i nazwisko klienta
     * @param email adres email klienta
     * @param phone numer telefonu klienta
     * @param role rola użytkownika (automatycznie ustawiana na "Customer")
     * @param password hasło klienta
     */
    public Customer( String name, String email, String phone, String role, String password) {
        super(name, email, phone, "Customer", password);
    }

    /**
     * Pobiera listę zamówień złożonych przez klienta.
     * 
     * @return lista zamówień online klienta
     */
    public List<OnlineOrder> getOrders() {
        return orders;
    }

    /**
     * Ustawia listę zamówień klienta.
     * 
     * @param orders lista zamówień do przypisania
     */
    public void setOrders(List<OnlineOrder> orders) {
        this.orders = orders;
    }

    /**
     * Umożliwia klientowi przeglądanie dostępnych leków w aptece.
     * 
     * <p><strong>Uwaga:</strong> Obecnie metoda zwraca pustą listę.
     * W pełnej implementacji powinna pobierać leki z bazy danych przez odpowiedni serwis.</p>
     * 
     * @return lista dostępnych leków (obecnie pusta)
     */
    public List<Medicine> viewAvailableMedicines() {
        // Tu powinna byc logika pobierania lekow z bazy (np. przez serwis)
        System.out.println("Viewing available medicines");
        return new ArrayList<>();
    }

    /**
     * Umożliwia klientowi złożenie reklamacji dotyczącej leku.
     * 
     * @param description opis problemu z lekiem
     */
    public void reportComplaint(String description) {
        DrugComplaint complaint = new DrugComplaint(description, this);
        complaint.submitComplaint();
    }

    /**
     * Przeprowadza proces rejestracji klienta w systemie.
     * 
     * <p>Metoda tworzy nowy obiekt CustomerRegistration i inicjuje proces rejestracji.</p>
     */
    public void register() {
        CustomerRegistration reg = new CustomerRegistration();
        reg.registerCustomer();
        System.out.println("Customer registration processed.");
    }
}
