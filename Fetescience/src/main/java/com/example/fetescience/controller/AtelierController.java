package com.example.fetescience.controller;

import com.example.fetescience.model.Animateur;
import com.example.fetescience.model.Atelier;
import com.example.fetescience.model.Creneau;
import com.example.fetescience.model.Personne; // Or 'User' if you have a generic User class
import com.example.fetescience.service.AtelierService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/animateur/ateliers") // Groups all these URLs under this prefix
public class AtelierController {

    private final AtelierService atelierService;

    public AtelierController(AtelierService atelierService) {
        this.atelierService = atelierService;
    }
/// ***************************** Gestion de creation d'atelier ******************
    // 1. Display the Form (GET)
    @GetMapping("/nouveau")
    public String afficherFormulaireCreation(HttpSession session, Model model) {
        // Security Check: Is user logged in and is an Animateur?
        if (!isAnimateur(session)) {
            return "redirect:/login";
        }

        model.addAttribute("atelier", new Atelier());
        return "nouvel_atelier"; // We will create this HTML file next
    }

    // 2. Process the Form (POST)
    @PostMapping
    public String creerAtelier(@ModelAttribute Atelier atelier, HttpSession session) {
        // Security Check
        if (!isAnimateur(session)) {
            return "redirect:/login";
        }

        // KEY STEP: Bind the logged-in Animateur to the Atelier
        Animateur loggedInAnimateur = (Animateur) session.getAttribute("user");
        atelier.setAnimateur(loggedInAnimateur);

        // Save the "Shell" (Atelier without creneaux yet)
        Atelier savedAtelier = atelierService.create(atelier);

        // Redirect to the "Management" page where they will add Creneaux
        // (We will build this redirect target in the next step)
        return "redirect:/animateur/ateliers/" + savedAtelier.getId() + "/gestion";
    }

    /// ***************************** Gestion d'ajout de creneau à l'atelier ******************

    // The Management Page (GET)
    // Shows the Atelier details + List of Creneaux + Form to add a new one
    @GetMapping("/{id}/gestion")
    public String gererAtelier(@PathVariable Long id, HttpSession session, Model model) {
        if (!isAnimateur(session)) {
            return "redirect:/login";
        }

        // Fetch the Atelier
        Atelier atelier = atelierService.getById(id);

        // SECURITY : If they try to manage someone else's work, kick them out
        Animateur loggedUser = (Animateur) session.getAttribute("user");
        if (!atelier.getAnimateur().getId().equals(loggedUser.getId())) {
            return "redirect:/animateur_page";
        }

        model.addAttribute("atelier", atelier);
        // We create an empty Creneau for the "Add New" form
        model.addAttribute("nouveauCreneau", new Creneau());

        return "gestion_atelier";
    }

    // 4. Add a Creneau (POST)
    @PostMapping("/{id}/creneaux")
    public String ajouterCreneau(@PathVariable Long id, @ModelAttribute Creneau creneau, HttpSession session) {
        if (!isAnimateur(session)) {
            return "redirect:/login";
        }

        Atelier atelier = atelierService.getById(id);

        // Use the helper method in your Atelier entity to link them
        atelier.ajouterCreneau(creneau);

        // Saving the Atelier will automatically save the new Creneau
        // because of cascade = CascadeType.ALL in your model
        atelierService.create(atelier);

        return "redirect:/animateur/ateliers/" + id + "/gestion";
    }



    // Helper method for Session Security
    private boolean isAnimateur(HttpSession session) {
        Object user = session.getAttribute("user");
        return user instanceof Animateur;
    }
}