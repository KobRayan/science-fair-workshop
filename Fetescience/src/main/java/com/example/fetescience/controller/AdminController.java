package com.example.fetescience.controller;

import com.example.fetescience.model.Inscription;
import com.example.fetescience.model.StatutInscription;
import com.example.fetescience.repository.InscriptionRepository;
import com.example.fetescience.service.InscriptionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final InscriptionRepository inscriptionRepository;
    private final InscriptionService inscriptionService;

    public AdminController(InscriptionRepository inscriptionRepository,
                           InscriptionService inscriptionService) {
        this.inscriptionRepository = inscriptionRepository;
        this.inscriptionService = inscriptionService;
    }

    @GetMapping("/inscriptions")
    public String gererInscriptions(Model model) {
        List<Inscription> inscriptions = inscriptionRepository.findAll();
        model.addAttribute("inscriptions", inscriptions);
        return "admin_inscriptions";
    }

    @PostMapping("/inscriptions/valider/{id}")
    public String validerInscription(@PathVariable Long id,
                                     RedirectAttributes redirectAttributes) {
        try {
            Inscription inscription = inscriptionRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Inscription introuvable"));

            inscription.setStatut(StatutInscription.VALIDEE);
            inscriptionRepository.save(inscription);

            redirectAttributes.addFlashAttribute("success", "Inscription validée !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/inscriptions";
    }

    @PostMapping("/inscriptions/refuser/{id}")
    public String refuserInscription(@PathVariable Long id,
                                     RedirectAttributes redirectAttributes) {
        try {
            Inscription inscription = inscriptionRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Inscription introuvable"));

            inscription.setStatut(StatutInscription.REFUSEE);
            inscriptionRepository.save(inscription);

            redirectAttributes.addFlashAttribute("success", "Inscription refusée !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/inscriptions";
    }
}