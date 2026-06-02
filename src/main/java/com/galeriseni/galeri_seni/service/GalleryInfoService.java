// src/main/java/com/galeriseni/galeri_seni/service/GalleryInfoService.java
package com.galeriseni.galeri_seni.service;

import com.galeriseni.galeri_seni.entity.GalleryInfo;
import com.galeriseni.galeri_seni.repository.GalleryInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GalleryInfoService {

    private final GalleryInfoRepository galleryInfoRepository;

    public Optional<GalleryInfo> getInfo() {
        return galleryInfoRepository.findAll().stream().findFirst();
    }

    public GalleryInfo save(GalleryInfo info) {
        return galleryInfoRepository.save(info);
    }
}