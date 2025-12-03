package com.example.fetescience.controller;

import com.example.fetescience.model.Personne;
import com.example.fetescience.model.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    // (Note: nouvelle-inscription security should ideally be handled in InscriptionController similarly)
}