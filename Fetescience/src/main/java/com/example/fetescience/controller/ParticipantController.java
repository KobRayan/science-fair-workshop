package com.example.fetescience.controller;

import com.example.fetescience.model.*;
import com.example.fetescience.repository.*;
import com.example.fetescience.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/participant")
@PreAuthorize("hasAnyRole('PARTICIPANT', 'ADMIN')")
public class ParticipantController {

    private final InscriptionService inscriptionService;
    private final PersonneRepository personneRepository;

    public ParticipantController(InscriptionService inscriptionService,
                                 PersonneRepository personneRepository) {
        this.inscriptionService = inscriptionService;
        this.personneRepository = personneRepository;
    }

    /**
     * ✅ Page "Mes inscriptions" (utilise inscription.html)
     */
    @GetMapping("/inscriptions")
    public String mesInscriptions(Authentication authentication, Model model) {
        String email = authentication.getName();
        Personne personne = personneRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        List<Inscription> inscriptions = inscriptionService.getInscriptionsByEmail(email);

        model.addAttribute("participant", personne);
        model.addAttribute("inscriptions", inscriptions);

        // ✅ CHANGÉ : Utilise "inscription" au lieu de "mes_inscriptions"
        return "inscription";
    }

    /**
     * ✅ Se désinscrire d'un atelier
     */
    @PostMapping("/inscriptions/desinscrire/{id}")
    public String desinscrire(@PathVariable Long id,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes) {
        try {
            inscriptionService.desinscrire(id);
            redirectAttributes.addFlashAttribute("success", "Désinscription effectuée !");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/participant/inscriptions";
    }
}