// src/main/java/com/galeriseni/galeri_seni/repository/ExhibitionRepository.java
package com.galeriseni.galeri_seni.repository;

import com.galeriseni.galeri_seni.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {
    List<Exhibition> findByStatus(Exhibition.Status status);
}