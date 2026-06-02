// src/main/java/com/galeriseni/galeri_seni/controller/PublicController.java
package com.galeriseni.galeri_seni.controller;

import com.galeriseni.galeri_seni.entity.Exhibition;
import com.galeriseni.galeri_seni.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PublicController {

    private final ArtworkService artworkService;
    private final ArtistService artistService;
    private final ExhibitionService exhibitionService;
    private final GalleryInfoService galleryInfoService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        galleryInfoService.getInfo().ifPresent(info -> model.addAttribute("galleryInfo", info));
        model.addAttribute("approvedArtworks", artworkService.findApproved());
        model.addAttribute("ongoingExhibitions", exhibitionService.findByStatus(Exhibition.Status.ONGOING));
        model.addAttribute("totalArtworks", artworkService.countAll());
        model.addAttribute("totalArtists", artistService.countAll());
        model.addAttribute("totalExhibitions", exhibitionService.countAll());
        return "public/home";
    }

    @GetMapping("/gallery")
    public String gallery(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String query,
            Model model) {
        model.addAttribute("artworks", artworkService.findApprovedFiltered(category, query));
        model.addAttribute("selectedCategory", category != null ? category : "");
        model.addAttribute("searchQuery", query != null ? query : "");
        return "public/gallery";
    }

    @GetMapping("/exhibition")
    public String exhibition(Model model) {
        model.addAttribute("ongoingExhibitions", exhibitionService.findByStatus(Exhibition.Status.ONGOING));
        model.addAttribute("upcomingExhibitions", exhibitionService.findByStatus(Exhibition.Status.UPCOMING));
        model.addAttribute("pastExhibitions", exhibitionService.findByStatus(Exhibition.Status.PAST));
        return "public/exhibition";
    }

    @GetMapping("/artists")
    public String artists(Model model) {
        model.addAttribute("artists", artistService.findAll());
        return "public/artists";
    }

    @GetMapping("/about")
    public String about(Model model) {
        galleryInfoService.getInfo().ifPresent(info -> model.addAttribute("galleryInfo", info));
        return "public/about";
    }
}