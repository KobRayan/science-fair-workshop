package com.example.fetescience.controller;

import com.example.fetescience.model.Inscription;
import com.example.fetescience.model.Participant;
import com.example.fetescience.service.InscriptionService;
import com.example.fetescience.service.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class InscriptionController {

    private final InscriptionService inscriptionService;
    private final ParticipantService participantService;  // 🆕 AJOUTÉ

    // 🆕 CONSTRUCTEUR MODIFIÉ avec les 2 services
    public InscriptionController(InscriptionService inscriptionService,
                                 ParticipantService participantService) {
        this.inscriptionService = inscriptionService;
        this.participantService = participantService;
    }

    @GetMapping("/nouvelle-inscription")
    public String afficherFormulaireInscription(Model model) {
        return "nouvelle_inscription";
    }

    @PostMapping("/inscrire_atelier")
    public String inscrireAtelier(
            @RequestParam String nom,
            @RequestParam String code_atelier,
            RedirectAttributes redirectAttributes) {

        try {
            // 1️⃣ Trouver ou créer le participant
            Participant participant = participantService.findByNomOrCreate(nom.trim());

            // 2️⃣ Validation
            if (code_atelier == null || code_atelier.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("erreur",
                        "Le code atelier est obligatoire !");
                return "redirect:/nouvelle-inscription";
            }

            // 3️⃣ Créer l'inscription
            Inscription inscription = inscriptionService.creerInscription(
                    participant.getId(),
                    code_atelier.trim()
            );

            // 4️⃣ Message de succès
            redirectAttributes.addFlashAttribute("succes",
                    "Inscription réussie pour " + participant.getNom() +
                            " à l'atelier " + inscription.getCreneau().getAtelier().getTitre() + " !");

            // 5️⃣ Rediriger vers les inscriptions de ce participant
            return "redirect:/inscriptions/" + participant.getId();

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erreur", e.getMessage());
            return "redirect:/nouvelle-inscription";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur",
                    "Erreur lors de l'inscription. Veuillez réessayer.");
            e.printStackTrace();
            return "redirect:/nouvelle-inscription";
        }
    }

    @GetMapping("/inscriptions")
    public String afficherInscriptions(Model model) {
        try {
            Long participantId = 1L;
            List<Inscription> inscriptions =
                    inscriptionService.getInscriptionsByParticipant(participantId);

            model.addAttribute("inscriptions", inscriptions);

            if (inscriptions.isEmpty()) {
                model.addAttribute("message",
                        "Vous n'avez aucune inscription pour le moment.");
            }

            return "inscriptions";

        } catch (Exception e) {
            model.addAttribute("erreur",
                    "Erreur lors du chargement de vos inscriptions.");
            e.printStackTrace();
            return "inscriptions";
        }
    }

    /**
     * 🆕 AFFICHER LES INSCRIPTIONS D'UN PARTICIPANT SPÉCIFIQUE
     */
    @GetMapping("/inscriptions/{participantId}")
    public String afficherInscriptionsParticipant(
            @PathVariable Long participantId,
            Model model) {

        try {
            Participant participant = participantService.findById(participantId)
                    .orElseThrow(() -> new RuntimeException("Participant introuvable"));

            List<Inscription> inscriptions =
                    inscriptionService.getInscriptionsByParticipant(participantId);

            model.addAttribute("participant", participant);
            model.addAttribute("inscriptions", inscriptions);

            if (inscriptions.isEmpty()) {
                model.addAttribute("message",
                        "Aucune inscription pour le moment.");
            }

            return "inscriptions";

        } catch (Exception e) {
            model.addAttribute("erreur", e.getMessage());
            return "inscriptions";
        }
    }

    @GetMapping("/desinscription/{id}")
    @ResponseBody
    public ResponseEntity<String> seDesinscrire(@PathVariable Long id) {
        try {
            Long participantId = 1L;
            boolean succes = inscriptionService.supprimerInscription(id, participantId);

            if (succes) {
                return ResponseEntity.ok("Désinscription réussie");
            } else {
                return ResponseEntity.status(404)
                        .body("Inscription non trouvée");
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400)
                    .body(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Erreur serveur : " + e.getMessage());
        }
    }
}