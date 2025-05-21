package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.model.Customer;
import com.systemzarzadzaniaapteka.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Kontroler REST API do zarządzania klientami apteki.
 * 
 * <p>Udostępnia endpointy HTTP do wykonywania operacji CRUD na klientach:</p>
 * <ul>
 *   <li>Pobieranie listy wszystkich klientów</li>
 *   <li>Pobieranie szczegółów konkretnego klienta</li>
 *   <li>Rejestracja nowych klientów</li>
 *   <li>Aktualizacja danych klientów</li>
 *   <li>Usuwanie klientów z systemu</li>
 * </ul>
 * 
 * <p>Wszystkie endpointy są dostępne pod ścieżką bazową: {@code /api/customers}</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    /** Repozytorium do dostępu do danych klientów */
    private final CustomerRepository customerRepository;

    /**
     * Konstruktor kontrolera z wstrzykiwaniem zależności.
     * 
     * @param customerRepository repozytorium klientów
     */
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Pobiera listę wszystkich klientów zarejestrowanych w systemie.
     * 
     * <p><strong>Endpoint:</strong> {@code GET /api/customers}</p>
     * 
     * @return lista wszystkich klientów
     */
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Pobiera szczegóły konkretnego klienta na podstawie jego identyfikatora.
     * 
     * <p><strong>Endpoint:</strong> {@code GET /api/customers/{id}}</p>
     * 
     * @param id unikalny identyfikator klienta
     * @return obiekt klienta o podanym identyfikatorze lub null jeśli nie znaleziono
     */
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    /**
     * Rejestruje nowego klienta w systemie.
     * 
     * <p><strong>Endpoint:</strong> {@code POST /api/customers}</p>
     * <p><strong>Content-Type:</strong> application/json</p>
     * 
     * @param customer obiekt klienta do zarejestrowania
     * @return zarejestrowany klient z przypisanym identyfikatorem
     */
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Aktualizuje dane istniejącego klienta.
     * 
     * <p><strong>Endpoint:</strong> {@code PUT /api/customers/{id}}</p>
     * <p><strong>Content-Type:</strong> application/json</p>
     * 
     * @param id identyfikator klienta do aktualizacji
     * @param customerDetails obiekt z nowymi danymi klienta
     * @return zaktualizowany klient lub null jeśli nie znaleziono klienta o podanym ID
     */
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) return null;
        
        customer.setEmail(customerDetails.getEmail());
        customer.setName(customerDetails.getName());
        customer.setPhone(customerDetails.getPhone());
        // inne pola
        
        return customerRepository.save(customer);
    }

    /**
     * Usuwa klienta z systemu na podstawie jego identyfikatora.
     * 
     * <p><strong>Endpoint:</strong> {@code DELETE /api/customers/{id}}</p>
     * 
     * @param id identyfikator klienta do usunięcia
     */
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
    }
}
