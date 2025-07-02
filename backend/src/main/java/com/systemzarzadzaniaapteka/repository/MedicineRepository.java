package com.systemzarzadzaniaapteka.repository;

import com.systemzarzadzaniaapteka.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repozytorium do dostępu do danych leków w bazie danych.
 * 
 * <p>Interfejs rozszerza JpaRepository, co zapewnia automatyczne implementacje
 * standardowych operacji CRUD (Create, Read, Update, Delete) dla encji Medicine.</p>
 * 
 * <p>Dzięki dziedziczeniu po JpaRepository, repozytorium automatycznie udostępnia metody takie jak:</p>
 * <ul>
 *   <li>{@code findAll()} - pobieranie wszystkich leków</li>
 *   <li>{@code findById(Long id)} - pobieranie leku po ID</li>
 *   <li>{@code save(Medicine medicine)} - zapisywanie/aktualizacja leku</li>
 *   <li>{@code deleteById(Long id)} - usuwanie leku po ID</li>
 *   <li>{@code count()} - liczenie wszystkich leków</li>
 *   <li>{@code existsById(Long id)} - sprawdzanie istnienia leku</li>
 * </ul>
 * 
 * <p>W przyszłości można dodać niestandardowe metody zapytań używając konwencji nazewnictwa Spring Data JPA
 * lub adnotacji @Query.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
}
