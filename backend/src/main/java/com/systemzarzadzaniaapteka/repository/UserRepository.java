package com.systemzarzadzaniaapteka.repository;

import com.systemzarzadzaniaapteka.model.AppUser;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.query.Param;

        import java.util.Optional;

/**
 * Interfejs repozytorium dla użytkowników w systemie zarządzania apteką.
 * 
 * <p>Interfejs UserRepository rozszerza JpaRepository i zapewnia metody do zarządzania encjami AppUser,
 * w tym sprawdzanie istnienia użytkownika na podstawie adresu e-mail oraz wyszukiwanie użytkownika po e-mailu.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
public interface UserRepository extends JpaRepository<AppUser, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT u FROM AppUser u WHERE u.email = :email")
    Optional<AppUser> findByEmail(@Param("email") String email);
}
