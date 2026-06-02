// src/main/java/com/galeriseni/galeri_seni/service/CurationService.java
package com.galeriseni.galeri_seni.service;

import com.galeriseni.galeri_seni.entity.Artwork;
import com.galeriseni.galeri_seni.entity.Curation;
import com.galeriseni.galeri_seni.entity.User;
import com.galeriseni.galeri_seni.repository.ArtworkRepository;
import com.galeriseni.galeri_seni.repository.CurationRepository;
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
public class CurationService {

    private final CurationRepository curationRepository;
    private final ArtworkRepository artworkRepository;
    private final UserRepository userRepository;

    public List<Curation> findAll() { return curationRepository.findAll(); }

    public List<Curation> findByCuratorId(Long curatorId) {
        return curationRepository.findByCuratorId(curatorId);
    }

    public Optional<Curation> findByArtworkId(Long artworkId) {
        return curationRepository.findByArtworkId(artworkId);
    }

    public Curation saveCuration(Long artworkId, Long curatorId,
                                 String notes, Artwork.CurationStatus status) {
        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new IllegalArgumentException("Karya tidak ditemukan"));
        User curator = userRepository.findById(curatorId)
                .orElseThrow(() -> new IllegalArgumentException("Kurator tidak ditemukan"));

        Curation curation = curationRepository.findByArtworkId(artworkId)
                .orElse(new Curation());

        curation.setArtwork(artwork);
        curation.setCurator(curator);
        curation.setNotes(notes);
        curation.setStatus(status);
        curation.setCuratedAt(LocalDateTime.now());

        artwork.setCurationStatus(status);
        artworkRepository.save(artwork);

        return curationRepository.save(curation);
    }

    public void delete(Long id) { curationRepository.deleteById(id); }

    public long countByCuratorId(Long curatorId) {
        return curationRepository.countByCuratorId(curatorId);
    }

    // ✨ TAMBAHAN METHOD BARU UNTUK EDIT/UPDATE ✨
    public void updateCuration(Long id, Long artworkId, Long curatorId,
                               String notes, Artwork.CurationStatus status) {
        Curation curation = curationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Data kurasi tidak ditemukan dengan ID: " + id));

        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new IllegalArgumentException("Karya tidak ditemukan"));
        User curator = userRepository.findById(curatorId)
                .orElseThrow(() -> new IllegalArgumentException("Kurator tidak ditemukan"));

        curation.setArtwork(artwork);
        curation.setCurator(curator);
        curation.setNotes(notes);
        curation.setStatus(status);
        curation.setCuratedAt(LocalDateTime.now()); // Perbarui timestamp waktu edit

        // Sinkronkan perubahan status ke table Artwork
        artwork.setCurationStatus(status);
        artworkRepository.save(artwork);

        curationRepository.save(curation);
    }

    // ✨ TAMBAHAN METHOD BARU UNTUK HAPUS (ALIASED KE deleteBYId) ✨
    public void deleteById(Long id) {
        if (!curationRepository.existsById(id)) {
            throw new IllegalArgumentException("Data kurasi tidak ditemukan dengan ID: " + id);
        }
        curationRepository.deleteById(id);
    }
}