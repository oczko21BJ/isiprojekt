package com.systemzarzadzaniaapteka;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Główna klasa aplikacji Spring Boot dla Systemu Zarządzania Apteką.
 * 
 * <p>Ta klasa służy jako punkt wejścia dla całej aplikacji i konfiguruje
 * automatyczne skanowanie komponentów Spring w pakiecie com.systemzarzadzaniaapteka.</p>
 * 
 * <p>System zarządzania apteką umożliwia:</p>
 * <ul>
 *   <li>Zarządzanie lekami i stanem magazynowym</li>
 *   <li>Obsługę zamówień i płatności</li>
 *   <li>Zarządzanie klientami i kartami lojalnościowymi</li>
 *   <li>Obsługę recept elektronicznych</li>
 *   <li>Generowanie raportów i analiz</li>
 * </ul>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
//@EntityScan("com.systemzarzadzaniaapteka.model") // Explicitly scan your entity package

public class SystemZarzadzaniaAptekaApplication {
    
    /**
     * Główna metoda uruchamiająca aplikację Spring Boot.
     * 
     * @param args argumenty wiersza poleceń przekazane do aplikacji
     */
    public static void main(String[] args) {
        SpringApplication.run(SystemZarzadzaniaAptekaApplication.class, args);
    }
}
