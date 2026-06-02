// src/main/java/com/galeriseni/galeri_seni/entity/GalleryInfo.java
package com.galeriseni.galeri_seni.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "gallery_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GalleryInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gallery_name", length = 200)
    private String galleryName;

    @Column(length = 300)
    private String tagline;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 300)
    private String address;

    @Column(name = "opening_hours", length = 200)
    private String openingHours;

    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    @Column(name = "founded_year")
    private Integer foundedYear;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}