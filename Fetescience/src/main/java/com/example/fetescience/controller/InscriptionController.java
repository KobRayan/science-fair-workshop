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
    private final ParticipantService participantService;

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
            // 1. Trouver le participant existant (Recherche sécurisée)
            // Si le participant n'existe pas, on lance une erreur (car pas de création de compte pour l'instant)
            Participant participant = participantService.findByNom(nom.trim())
                    .orElseThrow(() -> new IllegalArgumentException("Participant '" + nom + "' introuvable. Veuillez utiliser un compte existant (ex: Alice, Bob)."));

            // 2. Validation
            if (code_atelier == null || code_atelier.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("erreur", "Le code atelier est obligatoire !");
                return "redirect:/nouvelle-inscription";
            }

            // 3. Créer l'inscription
            Inscription inscription = inscriptionService.creerInscription(
                    participant.getId(),
                    code_atelier.trim()
            );

            // 4. Message de succès
            redirectAttributes.addFlashAttribute("succes",
                    "Inscription réussie pour " + participant.getNom() +
                            " à l'atelier " + inscription.getCreneau().getAtelier().getTitre() + " !");

            // 5. Rediriger vers les inscriptions de ce participant
            return "redirect:/inscriptions/" + participant.getId();

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erreur", e.getMessage());
            return "redirect:/nouvelle-inscription";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors de l'inscription.");
            e.printStackTrace();
            return "redirect:/nouvelle-inscription";
        }
    }

    // Affichage par défaut (Pour le dev, on force l'ID 1 si aucun ID n'est fourni)
    @GetMapping("/inscriptions")
    public String afficherInscriptionsDefaut(Model model) {
        // En mode dev, on redirige vers le participant 1 (Alice normalement)
        // ou on affiche une page vide.
        return "redirect:/inscriptions/1";
    }

    @GetMapping("/inscriptions/{participantId}")
    public String afficherInscriptionsParticipant(
            @PathVariable Long participantId,
            Model model) {

        try {
            // Utilise la méthode findById du service (retourne Optional)
            Participant participant = participantService.findById(participantId)
                    .orElseThrow(() -> new RuntimeException("Participant introuvable"));

            List<Inscription> inscriptions =
                    inscriptionService.getInscriptionsByParticipant(participantId);

            model.addAttribute("participant", participant);
            model.addAttribute("inscriptions", inscriptions);

            if (inscriptions.isEmpty()) {
                model.addAttribute("message", "Aucune inscription pour le moment.");
            }

            return "inscriptions";

        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur : " + e.getMessage());
            return "inscriptions";
        }
    }

    @GetMapping("/desinscription/{id}")
    @ResponseBody
    public ResponseEntity<String> seDesinscrire(@PathVariable Long id) {
        try {
            // TODO: Dans le futur, récupérer l'ID du participant connecté
            // Pour l'instant, on suppose que c'est l'utilisateur ID 1 (Alice)
            Long participantId = 1L;

            boolean succes = inscriptionService.supprimerInscription(id, participantId);

            if (succes) {
                return ResponseEntity.ok("Désinscription réussie");
            } else {
                return ResponseEntity.status(404).body("Inscription non trouvée");
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur serveur : " + e.getMessage());
        }
    }
}