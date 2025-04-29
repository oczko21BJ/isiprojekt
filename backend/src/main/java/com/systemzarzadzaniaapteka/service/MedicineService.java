package com.systemzarzadzaniaapteka.service;

import com.systemzarzadzaniaapteka.model.Medicine;
import com.systemzarzadzaniaapteka.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serwis do obsługi logiki biznesowej związanej z zarządzaniem lekami.
 * 
 * <p>Klasa zawiera metody do wykonywania operacji biznesowych na lekach,
 * w tym operacje CRUD oraz dodatkową logikę walidacji i przetwarzania danych.</p>
 * 
 * <p>Serwis pełni rolę warstwy pośredniczącej między kontrolerami a warstwą dostępu do danych,
 * implementując reguły biznesowe specyficzne dla zarządzania apteką.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Service
public class MedicineService {

    /** Repozytorium do dostępu do danych leków */
    private final MedicineRepository medicineRepository;

    /**
     * Konstruktor serwisu z wstrzykiwaniem zależności.
     * 
     * @param medicineRepository repozytorium leków
     */
    @Autowired
    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    /**
     * Pobiera listę wszystkich leków z bazy danych.
     * 
     * @return lista wszystkich leków dostępnych w systemie
     */
    public List<Medicine> getAll() {
        return medicineRepository.findAll();
    }

    /**
     * Pobiera lek na podstawie jego unikalnego identyfikatora.
     * 
     * @param id unikalny identyfikator leku
     * @return obiekt leku jeśli zostanie znaleziony, null w przeciwnym razie
     */
    public Medicine getById(Long id) {
        return medicineRepository.findById(id).orElse(null);
    }

    /**
     * Zapisuje nowy lek lub aktualizuje istniejący w bazie danych.
     * 
     * @param medicine obiekt leku do zapisania
     * @return zapisany lek z przypisanym identyfikatorem (jeśli był nowy)
     */
    public Medicine save(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    /**
     * Aktualizuje istniejący lek nowymi danymi.
     * 
     * <p>Metoda pobiera istniejący lek z bazy danych i aktualizuje jego pola
     * na podstawie przekazanych danych. Jeśli lek o podanym ID nie istnieje,
     * zwraca null.</p>
     * 
     * @param id identyfikator leku do aktualizacji
     * @param medicineDetails obiekt zawierający nowe dane leku
     * @return zaktualizowany lek lub null jeśli lek o podanym ID nie istnieje
     */
    public Medicine update(Long id, Medicine medicineDetails) {
        Medicine medicine = medicineRepository.findById(id).orElse(null);
        if (medicine == null) return null;
        
        medicine.setName(medicineDetails.getName());
        medicine.setDescription(medicineDetails.getDescription());
        medicine.setPrice(medicineDetails.getPrice());
        medicine.setQuantity(medicineDetails.getQuantity());
        // Dodaj inne pola jesli istnieja w modelu Medicine
        
        return medicineRepository.save(medicine);
    }

    /**
     * Usuwa lek z bazy danych na podstawie jego identyfikatora.
     * 
     * @param id identyfikator leku do usunięcia
     */
    public void delete(Long id) {
        medicineRepository.deleteById(id);
    }
}
