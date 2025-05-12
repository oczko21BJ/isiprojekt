package com.systemzarzadzaniaapteka.repository;

import com.systemzarzadzaniaapteka.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfejs repozytorium dla reklamacji w systemie zarządzania apteką.
 * 
 * <p>Interfejs ComplaintRepository rozszerza JpaRepository i zapewnia metody do zarządzania encjami Complaint.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}
