// src/main/java/com/galeriseni/galeri_seni/entity/AiAttribution.java
package com.galeriseni.galeri_seni.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_attributions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiAttribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id", nullable = false, unique = true)
    private Artwork artwork;

    @Enumerated(EnumType.STRING)
    @Column(name = "involvement_level", nullable = false)
    private InvolvementLevel involvementLevel;

    @Column(name = "software_used", length = 200)
    private String softwareUsed;

    @Column(name = "prompt_text", columnDefinition = "TEXT")
    private String promptText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recorded_by")
    private User recordedBy;

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt = LocalDateTime.now();

    public enum InvolvementLevel {
        PURE_HUMAN, AI_ASSISTED, FULLY_AI_GENERATED;

        public String getLabel() {
            return switch (this) {
                case PURE_HUMAN -> "Pure Human Art";
                case AI_ASSISTED -> "AI-Assisted";
                case FULLY_AI_GENERATED -> "Fully AI-Generated";
            };
        }
    }
}