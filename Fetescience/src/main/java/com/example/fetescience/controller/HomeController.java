package com.example.fetescience.controller;

import com.example.fetescience.model.Personne;
import com.example.fetescience.model.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Home";
    }

    // ✅ SECURED: Animateur Page
    @GetMapping("/animateur_page")
    public String animateurPage(HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");

        // Security Check: Must be logged in AND be an Animateur
        if (user == null || user.getRole() != Role.ANIMATEUR) {
            return "redirect:/"; // Kick them out to home
        }

        return "animateur_page";
    }


    @GetMapping("/test-error")
    public String testError(Model model) {
        // We manually add the attributes that Spring usually adds automatically
        model.addAttribute("status", 500);
        model.addAttribute("error", "NullPointerException: Le système a rencontré un problème inattendu.");
        return "error"; // Looks for src/main/resources/templates/error.html
    }


    // (Note: nouvelle-inscription security should ideally be handled in InscriptionController similarly)
}