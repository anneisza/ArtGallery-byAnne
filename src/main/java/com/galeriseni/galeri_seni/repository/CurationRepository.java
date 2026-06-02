// src/main/java/com/galeriseni/galeri_seni/repository/CurationRepository.java
package com.galeriseni.galeri_seni.repository;

import com.galeriseni.galeri_seni.entity.Artwork;
import com.galeriseni.galeri_seni.entity.Curation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CurationRepository extends JpaRepository<Curation, Long> {
    Optional<Curation> findByArtworkId(Long artworkId);
    List<Curation> findByCuratorId(Long curatorId);
    List<Curation> findByStatus(Artwork.CurationStatus status);
    long countByCuratorId(Long curatorId);
}