// src/test/java/com/systemzarzadzaniaapteka/repository/UserRepositoryIT.java
package com.systemzarzadzaniaapteka.repository;

import com.systemzarzadzaniaapteka.model.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryIT {

    @Autowired UserRepository repo;

    @Test
    void saveAndFindByEmail() {
        AppUser u = new AppUser("A","a@a.com","123","CLIENT","pw");
        repo.save(u);

        assertThat(repo.findByEmail("a@a.com")).isPresent();
        assertThat(repo.existsByEmail("a@a.com")).isTrue();
    }
}
