// src/main/java/com/galeriseni/galeri_seni/repository/GalleryInfoRepository.java
package com.galeriseni.galeri_seni.repository;

import com.galeriseni.galeri_seni.entity.GalleryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryInfoRepository extends JpaRepository<GalleryInfo, Long> {
}