// src/main/java/com/galeriseni/galeri_seni/entity/Artwork.java
package com.galeriseni.galeri_seni.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "artworks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "year_created")
    private Integer yearCreated;

    @Enumerated(EnumType.STRING)
    @Column(name = "curation_status")
    private CurationStatus curationStatus = CurationStatus.PENDING;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(mappedBy = "artwork", cascade = CascadeType.ALL)
    private AiAttribution aiAttribution;

    @OneToOne(mappedBy = "artwork", cascade = CascadeType.ALL)
    private Curation curation;

    public enum CurationStatus {
        PENDING, APPROVED, REJECTED
    }
}