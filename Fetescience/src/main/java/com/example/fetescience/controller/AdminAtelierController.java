package com.example.fetescience.controller;

import com.example.fetescience.model.Atelier;
import com.example.fetescience.model.Creneau;
import com.example.fetescience.repository.AtelierRepository;
import com.example.fetescience.repository.CreneauRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/ateliers")
@PreAuthorize("hasRole('ADMIN')")
public class AdminAtelierController {

    private final AtelierRepository atelierRepository;
    private final CreneauRepository creneauRepository;

    public AdminAtelierController(AtelierRepository atelierRepository,
                                  CreneauRepository creneauRepository) {
        this.atelierRepository = atelierRepository;
        this.creneauRepository = creneauRepository;
    }

    @GetMapping
    public String gererAteliers(Model model) {
        List<Atelier> ateliers = atelierRepository.findAll();
        List<Creneau> tousLesCreneaux = creneauRepository.findAll();

        long totalCreneaux = tousLesCreneaux.size();
        long creneauxComplets = tousLesCreneaux.stream()
                .filter(Creneau::isComplet)
                .count();
        long creneauxDisponibles = totalCreneaux - creneauxComplets;

        model.addAttribute("ateliers", ateliers);
        model.addAttribute("totalCreneaux", totalCreneaux);
        model.addAttribute("creneauxComplets", creneauxComplets);
        model.addAttribute("creneauxDisponibles", creneauxDisponibles);

        return "Admin_ateliers";
    }

    @PostMapping("/creneaux/ajouter")
    public String ajouterCreneau(@RequestParam Long atelierId,
                                 @RequestParam int horaireDebut,
                                 @RequestParam int duree,
                                 @RequestParam String lieu,
                                 @RequestParam int capacite,
                                 RedirectAttributes redirectAttributes) {
        try {
            Atelier atelier = atelierRepository.findById(atelierId)
                    .orElseThrow(() -> new IllegalArgumentException("Atelier introuvable"));

            Creneau nouveauCreneau = new Creneau(horaireDebut, duree, lieu, capacite);
            atelier.ajouterCreneau(nouveauCreneau);

            creneauRepository.save(nouveauCreneau);
            atelierRepository.save(atelier);

            redirectAttributes.addFlashAttribute("success",
                    "Créneau ajouté avec succès : " + horaireDebut + "h à " + lieu);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Erreur lors de l'ajout du créneau : " + e.getMessage());
        }

        return "redirect:/admin/ateliers";
    }

    @PostMapping("/creneaux/modifier")
    public String modifierCreneau(@RequestParam Long creneauId,
                                  @RequestParam int horaireDebut,
                                  @RequestParam int duree,
                                  @RequestParam String lieu,
                                  @RequestParam int capacite,
                                  RedirectAttributes redirectAttributes) {
        try {
            Creneau creneau = creneauRepository.findById(creneauId)
                    .orElseThrow(() -> new IllegalArgumentException("Créneau introuvable"));

            int nbInscriptions = creneau.getInscriptions().size();
            if (capacite < nbInscriptions) {
                redirectAttributes.addFlashAttribute("error",
                        "Impossible de réduire la capacité en dessous de " + nbInscriptions +
                                " (nombre d'inscriptions actuelles)");
                return "redirect:/admin/ateliers";
            }

            creneau.setHoraireDebut(horaireDebut);
            creneau.setDuree(duree);
            creneau.setLieu(lieu);
            creneau.setCapacite(capacite);
            creneau.setStatut(creneau.isComplet());

            creneauRepository.save(creneau);

            redirectAttributes.addFlashAttribute("success",
                    "Créneau modifié avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Erreur lors de la modification : " + e.getMessage());
        }

        return "redirect:/admin/ateliers";
    }

    @PostMapping("/creneaux/supprimer/{id}")
    public String supprimerCreneau(@PathVariable Long id,
                                   RedirectAttributes redirectAttributes) {
        try {
            Creneau creneau = creneauRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Créneau introuvable"));

            if (!creneau.getInscriptions().isEmpty()) {
                redirectAttributes.addFlashAttribute("error",
                        "Impossible de supprimer un créneau avec des inscriptions actives. " +
                                "Veuillez d'abord gérer les " + creneau.getInscriptions().size() + " inscription(s).");
                return "redirect:/admin/ateliers";
            }

            creneauRepository.delete(creneau);
            redirectAttributes.addFlashAttribute("success",
                    "Créneau supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Erreur lors de la suppression : " + e.getMessage());
        }

        return "redirect:/admin/ateliers";
    }

    @PostMapping("/ajouter")
    public String ajouterAtelier(@RequestParam String titre,
                                 RedirectAttributes redirectAttributes) {
        try {
            Atelier nouvelAtelier = new Atelier(titre);
            atelierRepository.save(nouvelAtelier);

            redirectAttributes.addFlashAttribute("success",
                    "Atelier '" + titre + "' créé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Erreur lors de la création de l'atelier : " + e.getMessage());
        }

        return "redirect:/admin/ateliers";
    }
}