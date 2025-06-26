package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.model.Medicine;
import com.systemzarzadzaniaapteka.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

/**
 * Kontroler REST API do zarządzania lekami w systemie apteki.
 * 
 * <p>Udostępnia endpointy HTTP do wykonywania operacji CRUD na lekach:</p>
 * <ul>
 *   <li>Pobieranie listy wszystkich leków</li>
 *   <li>Pobieranie szczegółów konkretnego leku</li>
 *   <li>Dodawanie nowych leków do systemu</li>
 *   <li>Aktualizacja informacji o lekach</li>
 *   <li>Usuwanie leków z systemu</li>
 * </ul>
 * 
 * <p>Wszystkie endpointy są dostępne pod ścieżką bazową: {@code /api/medicines}</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    /** Serwis do obsługi logiki biznesowej związanej z lekami */
    @Autowired
    private MedicineService medicineService;

    /**
     * Pobiera listę wszystkich leków dostępnych w systemie.
     * 
     * <p><strong>Endpoint:</strong> {@code GET /api/medicines}</p>
     * 
     * @return lista wszystkich leków w systemie
     */
    @GetMapping
    public List<Medicine> getAll() {
        return medicineService.getAll();
    }

    /**
     * Pobiera szczegóły konkretnego leku na podstawie jego identyfikatora.
     * 
     * <p><strong>Endpoint:</strong> {@code GET /api/medicines/{id}}</p>
     * 
     * @param id unikalny identyfikator leku
     * @return obiekt leku o podanym identyfikatorze lub null jeśli nie znaleziono
     */
    @GetMapping("/{id}")
    public Medicine getById(@PathVariable Long id) {
        return medicineService.getById(id);
    }

    /**
     * Tworzy nowy lek w systemie.
     * 
     * <p><strong>Endpoint:</strong> {@code POST /api/medicines}</p>
     * <p><strong>Content-Type:</strong> application/json</p>
     * 
     * @param medicine obiekt leku do utworzenia (walidowany)
     * @return utworzony lek z przypisanym identyfikatorem
     */
    @PostMapping
    public Medicine create(@RequestBody @Valid Medicine medicine) {
        return medicineService.save(medicine);
    }

    /**
     * Aktualizuje istniejący lek w systemie.
     * 
     * <p><strong>Endpoint:</strong> {@code PUT /api/medicines/{id}}</p>
     * <p><strong>Content-Type:</strong> application/json</p>
     * 
     * @param id identyfikator leku do aktualizacji
     * @param medicine obiekt z nowymi danymi leku (walidowany)
     * @return zaktualizowany lek lub null jeśli nie znaleziono leku o podanym ID
     */
    @PutMapping("/{id}")
    public Medicine update(@PathVariable Long id, @RequestBody @Valid Medicine medicine) {
        return medicineService.update(id, medicine);
    }

    /**
     * Usuwa lek z systemu na podstawie jego identyfikatora.
     * 
     * <p><strong>Endpoint:</strong> {@code DELETE /api/medicines/{id}}</p>
     * 
     * @param id identyfikator leku do usunięcia
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        medicineService.delete(id);
    }
}
