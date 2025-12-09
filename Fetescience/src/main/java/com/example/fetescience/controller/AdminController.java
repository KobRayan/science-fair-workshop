package com.example.fetescience.controller;

import com.example.fetescience.model.Inscription;
import com.example.fetescience.model.StatutInscription;
import com.example.fetescience.service.InscriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final InscriptionService inscriptionService;

    public AdminController(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    // Page principale admin : liste de toutes les inscriptions
    @GetMapping("/inscriptions")
    public String toutesLesInscriptions(Model model) {
        List<Inscription> inscriptions = inscriptionService.findAll();
        model.addAttribute("inscriptions", inscriptions);
        return "admin_inscriptions";
    }

    // Accepter une inscription
    @PostMapping("/inscriptions/{id}/accepter")
    public String accepterInscription(@PathVariable Long id) {
        Inscription inscription = inscriptionService.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
        inscription.setStatut(StatutInscription.VALIDEE);
        inscriptionService.update(inscription);
        return "redirect:/admin/inscriptions";
    }

    // Refuser une inscription
    @PostMapping("/inscriptions/{id}/refuser")
    public String refuserInscription(@PathVariable Long id) {
        Inscription inscription = inscriptionService.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
        inscription.setStatut(StatutInscription.REFUSEE);
        inscriptionService.update(inscription);
        return "redirect:/admin/inscriptions";
    }
}
