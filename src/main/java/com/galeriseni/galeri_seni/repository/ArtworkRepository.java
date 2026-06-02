package com.galeriseni.galeri_seni.repository;

import com.galeriseni.galeri_seni.entity.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    List<Artwork> findByCurationStatus(Artwork.CurationStatus status);
    List<Artwork> findByArtistId(Long artistId);
    List<Artwork> findByExhibitionId(Long exhibitionId);
    long countByCurationStatus(Artwork.CurationStatus status);

    // MENGGUNAKAN CAST AS STRING AGAR ENUM MYSQL TERBACA SEBAGAI TEXT
    @Query("SELECT a FROM Artwork a LEFT JOIN a.aiAttribution ai WHERE " +
            "a.curationStatus = com.galeriseni.galeri_seni.entity.Artwork$CurationStatus.APPROVED AND " +
            "(:query IS NULL OR :query = '' OR LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
            "(:category IS NULL OR :category = '' OR CAST(ai.involvementLevel AS string) = :category)")
    List<Artwork> findApprovedWithSearchAndFilter(@Param("category") String category, @Param("query") String query);
}