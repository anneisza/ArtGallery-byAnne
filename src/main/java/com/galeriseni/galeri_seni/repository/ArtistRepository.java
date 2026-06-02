// src/main/java/com/galeriseni/galeri_seni/repository/ArtistRepository.java
package com.galeriseni.galeri_seni.repository;

import com.galeriseni.galeri_seni.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}