// src/main/java/com/galeriseni/galeri_seni/service/ArtworkService.java
package com.galeriseni.galeri_seni.service;

import com.galeriseni.galeri_seni.entity.Artist;
import com.galeriseni.galeri_seni.entity.Artwork;
import com.galeriseni.galeri_seni.entity.Exhibition;
import com.galeriseni.galeri_seni.repository.ArtistRepository;
import com.galeriseni.galeri_seni.repository.ArtworkRepository;
import com.galeriseni.galeri_seni.repository.ExhibitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final ArtistRepository artistRepository;
    private final ExhibitionRepository exhibitionRepository;

    public List<Artwork> findAll() {
        return artworkRepository.findAll();
    }

    public List<Artwork> findApproved() {
        return artworkRepository.findByCurationStatus(Artwork.CurationStatus.APPROVED);
    }

    public Optional<Artwork> findById(Long id) {
        return artworkRepository.findById(id);
    }

    public Artwork save(Artwork artwork) {
        return artworkRepository.save(artwork);
    }

    public Artwork create(String title, Long artistId, Long exhibitionId,
                          String description, Integer yearCreated, String imageUrl) {
        Artwork artwork = new Artwork();
        artwork.setTitle(title);
        artwork.setDescription(description);
        artwork.setYearCreated(yearCreated);
        artwork.setImageUrl(imageUrl);
        artwork.setCurationStatus(Artwork.CurationStatus.PENDING);

        if (artistId != null) {
            artistRepository.findById(artistId).ifPresent(artwork::setArtist);
        }
        if (exhibitionId != null) {
            exhibitionRepository.findById(exhibitionId).ifPresent(artwork::setExhibition);
        }
        return artworkRepository.save(artwork);
    }

    public Artwork update(Long id, String title, Long artistId, Long exhibitionId,
                          String description, Integer yearCreated, String imageUrl) {
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Karya tidak ditemukan"));
        artwork.setTitle(title);
        artwork.setDescription(description);
        artwork.setYearCreated(yearCreated);
        if (imageUrl != null && !imageUrl.isBlank()) artwork.setImageUrl(imageUrl);

        if (artistId != null) {
            artistRepository.findById(artistId).ifPresent(artwork::setArtist);
        }
        if (exhibitionId != null) {
            exhibitionRepository.findById(exhibitionId).ifPresent(artwork::setExhibition);
        }
        return artworkRepository.save(artwork);
    }

    public void delete(Long id) {
        artworkRepository.deleteById(id);
    }

    public long countAll() { return artworkRepository.count(); }
    public long countPending() { return artworkRepository.countByCurationStatus(Artwork.CurationStatus.PENDING); }
    public long countApproved() { return artworkRepository.countByCurationStatus(Artwork.CurationStatus.APPROVED); }

    // Tambahkan di sini ↓
    public List<Artwork> findApprovedFiltered(String category, String query) {
        String cat = (category != null && !category.isBlank()) ? category : null;
        String q   = (query != null && !query.isBlank()) ? query : null;
        return artworkRepository.findApprovedWithSearchAndFilter(cat, q);
    }
}