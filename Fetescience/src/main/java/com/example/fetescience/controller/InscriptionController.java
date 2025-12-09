package com.example.fetescience.controller;

import com.example.fetescience.model.Inscription;
import com.example.fetescience.model.Participant;
import com.example.fetescience.service.InscriptionService;
import com.example.fetescience.service.ParticipantService;
import com.example.fetescience.service.AtelierService;
import com.example.fetescience.model.Personne;
import com.example.fetescience.model.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class InscriptionController {

    private final InscriptionService inscriptionService;
    private final ParticipantService participantService;
    private final AtelierService atelierService;

    public InscriptionController(InscriptionService inscriptionService,
                                 ParticipantService participantService,
                                 AtelierService atelierService) {
        this.inscriptionService = inscriptionService;
        this.participantService = participantService;
        this.atelierService = atelierService;
    }
    // --- 1. SHOW FORM (With Pre-filled Name) ---
    @GetMapping("/nouvelle-inscription")
    public String afficherFormulaireInscription(Model model, HttpSession session) {
        // Load ateliers
        model.addAttribute("ateliers", atelierService.listAll());

        // Check if user is logged in
        Personne user = (Personne) session.getAttribute("user");
        if (user != null && user.getRole() == Role.PARTICIPANT) {
            // Pre-fill the name field in the form
            model.addAttribute("preFilledName", user.getNom());
        }
        else if (user == null) {
            return "redirect:/auth/login"; // Kick them out to home
        } else if (user.getRole() != Role.PARTICIPANT && user.getRole() != Role.ADMIN) {
            return "redirect:/";
        }
        return "nouvelle_inscription";
    }

    // --- 2. PROCESS FORM (With Security Check) ---
    @PostMapping("/inscrire_atelier")
    public String inscrireAtelier(
            @RequestParam String nom,
            @RequestParam Long creneauId,
            HttpSession session, // Get session
            RedirectAttributes redirectAttributes) {

        try {
            // SECURITY CHECK: Matches Session?
            Personne user = (Personne) session.getAttribute("user");

            // If logged in, the name entered MUST match the session name
            if (user != null && user.getRole() == Role.PARTICIPANT) {
                if (!user.getNom().equalsIgnoreCase(nom.trim())) {
                    throw new IllegalArgumentException("Erreur de sécurité : Vous ne pouvez pas inscrire une autre personne avec votre compte.");
                }
            }

            // Find Participant
            Participant participant = participantService.findByNom(nom.trim())
                    .orElseThrow(() -> new IllegalArgumentException("Participant introuvable. (Etes-vous inscrit ?)"));

            // Create Inscription
            Inscription inscription = inscriptionService.creerInscription(
                    participant.getId(),
                    creneauId
            );

            redirectAttributes.addFlashAttribute("succes",
                    "Inscription réussie : " + inscription.getCreneau().getAtelier().getTitre());

            return "redirect:/inscriptions/" + participant.getId();

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", e.getMessage());
            return "redirect:/nouvelle-inscription";
        }
    }

    // ... (Keep other methods like afficherInscriptions, desinscription as they were) ...
    // Note: Ensure afficherInscriptions uses session logic too if you want to secure it later

    @GetMapping("/inscriptions/{participantId}")
    public String afficherInscriptionsParticipant(@PathVariable Long participantId, Model model, HttpSession session) {

            Personne user = (Personne) session.getAttribute("user"); //tries finding user so we check if he can access the /id
            // 3. SECURITY CHECK
            // Rule: You must be logged in AND (Be the owner OR Be an Admin)
            if (user == null) {
                return "redirect:/auth/login"; // Not logged in -> Login
            }

            System.out.println("User ID: " + user.getId() + " vs Requested ID: " + participantId);
            System.out.println("Role: " + user.getRole());
            boolean isOwner = user.getId().equals(participantId);
            boolean isAdmin = user.getRole() == Role.ADMIN;
            System.out.println("isOwner: " + isOwner);
            System.out.println("isAdmin: " + isAdmin);

            if (!isOwner && !isAdmin) {
                // Logged in as Alice (ID 3) but trying to see Bob (ID 4)
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous ne pouvez pas accéder aux inscriptions d'un autre utilisateur."); // Redirect to Home (Access Denied)
                // TODO : à changer à "redirect/404" peut etre
               //return "error/403";
            }

            try{

            // if good thn =>

                Participant participant = participantService.findById(participantId)
                    .orElseThrow(() -> new RuntimeException("Participant introuvable"));

                List<Inscription> inscriptions = inscriptionService.getInscriptionsByParticipant(participantId);

                model.addAttribute("participant", participant);
                model.addAttribute("inscriptions", inscriptions);

                 return "inscriptions";
        }
        catch (Exception e) {
            model.addAttribute("erreur", "Erreur : " + e.getMessage());
            return "inscriptions";
        }
    }


    // Affichage par défaut (Pour le dev, on force l'ID 1 si aucun ID n'est fourni)
    @GetMapping("/inscriptions")
    public String afficherInscriptionsDefaut(Model model) {
        // En mode dev, on redirige vers le participant 1 (Alice normalement)
        // ou on affiche une page vide.
        return "redirect:/inscriptions/1";
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