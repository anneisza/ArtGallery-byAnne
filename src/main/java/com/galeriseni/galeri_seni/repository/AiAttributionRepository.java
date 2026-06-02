// src/main/java/com/galeriseni/galeri_seni/repository/AiAttributionRepository.java
package com.galeriseni.galeri_seni.repository;

import com.galeriseni.galeri_seni.entity.AiAttribution;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AiAttributionRepository extends JpaRepository<AiAttribution, Long> {
    Optional<AiAttribution> findByArtworkId(Long artworkId);
}