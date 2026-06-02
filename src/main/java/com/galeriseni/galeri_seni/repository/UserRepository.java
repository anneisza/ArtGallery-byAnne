// src/main/java/com/galeriseni/galeri_seni/repository/UserRepository.java
package com.galeriseni.galeri_seni.repository;

import com.galeriseni.galeri_seni.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(User.Role role);
    boolean existsByEmail(String email);
    long countByRole(User.Role role);
}