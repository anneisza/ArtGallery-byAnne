// src/main/java/com/galeriseni/galeri_seni/service/ExhibitionService.java
package com.galeriseni.galeri_seni.service;

import com.galeriseni.galeri_seni.entity.Exhibition;
import com.galeriseni.galeri_seni.repository.ExhibitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;

    public List<Exhibition> findAll() { return exhibitionRepository.findAll(); }

    public List<Exhibition> findByStatus(Exhibition.Status status) {
        return exhibitionRepository.findByStatus(status);
    }

    public Optional<Exhibition> findById(Long id) { return exhibitionRepository.findById(id); }

    public Exhibition create(String title, String description, String location,
                             LocalDate startDate, LocalDate endDate, Exhibition.Status status) {
        Exhibition ex = new Exhibition();
        ex.setTitle(title);
        ex.setDescription(description);
        ex.setLocation(location);
        ex.setStartDate(startDate);
        ex.setEndDate(endDate);
        ex.setStatus(status);
        return exhibitionRepository.save(ex);
    }

    public Exhibition update(Long id, String title, String description, String location,
                             LocalDate startDate, LocalDate endDate, Exhibition.Status status) {
        Exhibition ex = exhibitionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pameran tidak ditemukan"));
        ex.setTitle(title);
        ex.setDescription(description);
        ex.setLocation(location);
        ex.setStartDate(startDate);
        ex.setEndDate(endDate);
        ex.setStatus(status);
        return exhibitionRepository.save(ex);
    }

    public void delete(Long id) { exhibitionRepository.deleteById(id); }

    public long countAll() { return exhibitionRepository.count(); }
}