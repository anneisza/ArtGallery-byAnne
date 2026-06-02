// src/main/java/com/galeriseni/galeri_seni/controller/KuratorController.java
package com.galeriseni.galeri_seni.controller;

import com.galeriseni.galeri_seni.entity.*;
import com.galeriseni.galeri_seni.repository.UserRepository;
import com.galeriseni.galeri_seni.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/kurator")
@RequiredArgsConstructor
public class KuratorController {

    private final CurationService curationService;
    private final AiAttributionService aiAttributionService;
    private final ArtworkService artworkService;
    private final UserService userService;
    private final UserRepository userRepository;

    private User getCurrentUser(Authentication auth) {
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalStateException("User tidak ditemukan"));
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        User user = getCurrentUser(auth);
        model.addAttribute("user", user);
        model.addAttribute("curations", curationService.findByCuratorId(user.getId()));
        model.addAttribute("totalCurations", curationService.countByCuratorId(user.getId()));
        model.addAttribute("totalArtworks", artworkService.countAll());
        model.addAttribute("totalPending", artworkService.countPending());
        model.addAttribute("totalAiAttributions", aiAttributionService.countAll());
        return "kurator/dashboard";
    }

    // ==========================================
    // 🎨 MANAGEMENT: KURASI SENI
    // ==========================================

    @GetMapping("/kurasi")
    public String daftarKurasi(Model model, Authentication auth) {
        User user = getCurrentUser(auth);
        model.addAttribute("user", user);
        model.addAttribute("curations", curationService.findByCuratorId(user.getId()));
        model.addAttribute("artworks", artworkService.findAll());
        return "kurator/kurasi";
    }

    @PostMapping("/kurasi/simpan")
    public String simpanKurasi(@RequestParam Long artworkId,
                               @RequestParam String notes,
                               @RequestParam Artwork.CurationStatus status,
                               Authentication auth,
                               RedirectAttributes redirectAttr) {
        User user = getCurrentUser(auth);
        curationService.saveCuration(artworkId, user.getId(), notes, status);
        redirectAttr.addFlashAttribute("successMessage", "Kurasi berhasil disimpan.");
        return "redirect:/kurator/kurasi";
    }

    @PostMapping("/kurasi/edit/{id}")
    public String editKurasi(@PathVariable Long id,
                             @RequestParam Long artworkId,
                             @RequestParam String notes,
                             @RequestParam Artwork.CurationStatus status,
                             Authentication auth,
                             RedirectAttributes redirectAttr) {
        User user = getCurrentUser(auth);
        curationService.updateCuration(id, artworkId, user.getId(), notes, status);
        redirectAttr.addFlashAttribute("successMessage", "Data kurasi berhasil diperbarui.");
        return "redirect:/kurator/kurasi";
    }

    @PostMapping("/kurasi/hapus/{id}")
    public String hapusKurasi(@PathVariable Long id, RedirectAttributes redirectAttr) {
        curationService.deleteById(id);
        redirectAttr.addFlashAttribute("successMessage", "Data log kurasi berhasil dihapus.");
        return "redirect:/kurator/kurasi";
    }

    // ==========================================
    // 🤖 MANAGEMENT: ATRIBUSI AI
    // ==========================================

    @GetMapping("/atribusi-ai")
    public String atribusiAi(Model model, Authentication auth) {
        User user = getCurrentUser(auth);
        model.addAttribute("user", user);
        model.addAttribute("attributions", aiAttributionService.findAll());
        model.addAttribute("artworks", artworkService.findAll());
        return "kurator/atribusi-ai";
    }

    @PostMapping("/atribusi-ai/simpan")
    public String simpanAtribusi(@RequestParam Long artworkId,
                                 @RequestParam AiAttribution.InvolvementLevel involvementLevel,
                                 @RequestParam(required = false) String softwareUsed,
                                 @RequestParam(required = false) String promptText,
                                 Authentication auth,
                                 RedirectAttributes redirectAttr) {
        User user = getCurrentUser(auth);
        aiAttributionService.saveAttribution(artworkId, user.getId(),
                involvementLevel, softwareUsed, promptText);
        redirectAttr.addFlashAttribute("successMessage", "Atribusi AI berhasil disimpan.");
        return "redirect:/kurator/atribusi-ai";
    }

    @PostMapping("/atribusi-ai/edit/{id}")
    public String editAtribusi(@PathVariable Long id,
                               @RequestParam Long artworkId,
                               @RequestParam AiAttribution.InvolvementLevel involvementLevel,
                               @RequestParam(required = false) String softwareUsed,
                               @RequestParam(required = false) String promptText,
                               Authentication auth,
                               RedirectAttributes redirectAttr) {
        User user = getCurrentUser(auth);
        aiAttributionService.updateAttribution(id, artworkId, user.getId(), involvementLevel, softwareUsed, promptText);
        redirectAttr.addFlashAttribute("successMessage", "Sertifikasi Atribusi AI berhasil diperbarui.");
        return "redirect:/kurator/atribusi-ai";
    }

    @PostMapping("/atribusi-ai/hapus/{id}")
    public String hapusAtribusi(@PathVariable Long id, RedirectAttributes redirectAttr) {
        aiAttributionService.deleteById(id);
        redirectAttr.addFlashAttribute("successMessage", "Sertifikasi Atribusi AI berhasil dihapus.");
        return "redirect:/kurator/atribusi-ai";
    }

    // ==========================================
    // 👤 MANAGEMENT: PROFIL & RIWAYAT
    // ==========================================

    @GetMapping("/riwayat")
    public String riwayat(Model model, Authentication auth) {
        User user = getCurrentUser(auth);
        model.addAttribute("user", user);
        model.addAttribute("curations", curationService.findByCuratorId(user.getId()));
        return "kurator/riwayat";
    }

    @GetMapping("/profil")
    public String profil(Model model, Authentication auth) {
        User user = getCurrentUser(auth);
        model.addAttribute("user", user);
        return "kurator/profil";
    }

    @PostMapping("/profil/update")
    public String updateProfil(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam(required = false) String newPassword,
                               Authentication auth,
                               RedirectAttributes redirectAttr) {
        User user = getCurrentUser(auth);
        userService.updateKurator(user.getId(), name, email, newPassword);
        redirectAttr.addFlashAttribute("successMessage", "Profil berhasil diperbarui.");
        return "redirect:/kurator/profil";
    }
}