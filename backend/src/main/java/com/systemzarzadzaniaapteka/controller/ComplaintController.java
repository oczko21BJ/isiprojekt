package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.model.Complaint;
import com.systemzarzadzaniaapteka.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
/**
 * Klasa kontrolera do obsługi skarg użytkowników.
 * Umożliwia zarządzanie skargami - pobieranie ich, tworzenie, rozwiązywanie oraz usuwanie.
 */
@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;
    /**
     * Endpoint do pobierania wszystkich skarg.
     *
     * Zwraca listę wszystkich skarg zapisanych w systemie.
     *
     * @return lista skarg.
     */
    @GetMapping
    public List<Complaint> getAll() {
        return complaintService.getAll();
    }
    /**
     * Endpoint do pobierania skargi po jej identyfikatorze.
     *
     * Zwraca szczegóły jednej skargi na podstawie jej unikalnego ID.
     *
     * @param id identyfikator skargi.
     * @return skarga o podanym identyfikatorze.
     */
    @GetMapping("/{id}")
    public Complaint getById(@PathVariable Long id) {
        return complaintService.getById(id);
    }
    /**
     * Endpoint do tworzenia nowej skargi.
     *
     * Przyjmuje dane skargi w formacie JSON, waliduje je, a następnie zapisuje skargę w systemie.
     *
     * @param complaint obiekt skargi do zapisania.
     * @return zapisany obiekt skargi.
     */
    @PostMapping
    public Complaint create(@RequestBody @Valid Complaint complaint) {
        return complaintService.save(complaint);
    }
    /**
     * Endpoint do rozwiązywania skargi.
     *
     * Umożliwia rozwiązanie skargi na podstawie jej identyfikatora.
     * Zaktualizowane dane skargi (np. status) są przekazywane w ciele żądania.
     *
     * @param id identyfikator skargi do rozwiązania.
     * @param complaint obiekt skargi z aktualizowanymi danymi.
     * @return zaktualizowana skarga.
     */
    @PutMapping("/{id}")
    public Complaint resolve(@PathVariable Long id, @RequestBody Complaint complaint) {
        return complaintService.resolve(id, complaint);
    }
    /**
     * Endpoint do usuwania skargi.
     *
     * Usuwa skargę o podanym identyfikatorze.
     *
     * @param id identyfikator skargi do usunięcia.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        complaintService.delete(id);
    }
}
