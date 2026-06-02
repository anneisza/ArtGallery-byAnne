// src/main/java/com/galeriseni/galeri_seni/service/AiAttributionService.java
package com.galeriseni.galeri_seni.service;

import com.galeriseni.galeri_seni.entity.AiAttribution;
import com.galeriseni.galeri_seni.entity.Artwork;
import com.galeriseni.galeri_seni.entity.User;
import com.galeriseni.galeri_seni.repository.AiAttributionRepository;
import com.galeriseni.galeri_seni.repository.ArtworkRepository;
import com.galeriseni.galeri_seni.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AiAttributionService {

    private final AiAttributionRepository aiAttributionRepository;
    private final ArtworkRepository artworkRepository;
    private final UserRepository userRepository;

    public List<AiAttribution> findAll() { return aiAttributionRepository.findAll(); }

    public Optional<AiAttribution> findByArtworkId(Long artworkId) {
        return aiAttributionRepository.findByArtworkId(artworkId);
    }

    public AiAttribution saveAttribution(Long artworkId, Long userId,
                                         AiAttribution.InvolvementLevel level,
                                         String softwareUsed, String promptText) {
        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new IllegalArgumentException("Karya tidak ditemukan"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User tidak ditemukan"));

        AiAttribution attribution = aiAttributionRepository.findByArtworkId(artworkId)
                .orElse(new AiAttribution());

        attribution.setArtwork(artwork);
        attribution.setRecordedBy(user); // Menggunakan properti recordedBy sesuai entity asli kamu
        attribution.setInvolvementLevel(level);
        attribution.setSoftwareUsed(softwareUsed);
        attribution.setPromptText(promptText);
        attribution.setRecordedAt(LocalDateTime.now());

        return aiAttributionRepository.save(attribution);
    }

    public void delete(Long id) { aiAttributionRepository.deleteById(id); }

    public long countAll() { return aiAttributionRepository.count(); }

    // ✨ TAMBAHAN METHOD BARU UNTUK EDIT/UPDATE ATRIBUSI AI ✨
    public void updateAttribution(Long id, Long artworkId, Long userId,
                                  AiAttribution.InvolvementLevel level,
                                  String softwareUsed, String promptText) {
        AiAttribution attribution = aiAttributionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Data atribusi AI tidak ditemukan dengan ID: " + id));

        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new IllegalArgumentException("Karya tidak ditemukan"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User tidak ditemukan"));

        attribution.setArtwork(artwork);
        attribution.setRecordedBy(user); // Disamakan dengan variable entity asli kamu
        attribution.setInvolvementLevel(level);
        attribution.setSoftwareUsed(softwareUsed);
        attribution.setPromptText(promptText);
        attribution.setRecordedAt(LocalDateTime.now()); // Perbarui waktu rekaman audit

        aiAttributionRepository.save(attribution);
    }

    // ✨ TAMBAHAN METHOD BARU UNTUK HAPUS (ALIASED KE deleteById) ✨
    public void deleteById(Long id) {
        if (!aiAttributionRepository.existsById(id)) {
            throw new IllegalArgumentException("Data atribusi AI tidak ditemukan dengan ID: " + id);
        }
        aiAttributionRepository.deleteById(id);
    }
}