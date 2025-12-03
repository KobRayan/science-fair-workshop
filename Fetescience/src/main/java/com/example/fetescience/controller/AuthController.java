package com.example.fetescience.controller;

import com.example.fetescience.model.Personne;
import com.example.fetescience.model.Role;
import com.example.fetescience.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // --- LOGIN PAGES ---

    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        // If already logged in, redirect to correct page
        Personne user = (Personne) session.getAttribute("user");
        if (user != null) {
            return redirectBasedOnRole(user);
        }
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {

        Personne user = authService.authenticate(email, password);

        if (user != null) {
            // ✅ SAVE USER IN SESSION
            session.setAttribute("user", user);
            return redirectBasedOnRole(user);
        } else {
            redirectAttributes.addFlashAttribute("erreur", "Email ou mot de passe incorrect");
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // --- REGISTRATION PAGES ---

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam String nom,
                                  @RequestParam String email,
                                  @RequestParam String password,
                                  @RequestParam String role, // "PARTICIPANT" or "ANIMATEUR"
                                  RedirectAttributes redirectAttributes) {
        try {
            // Convert String to Enum (safe because select options are fixed)
            Role userRole = Role.valueOf(role);

            // Only allow Participant or Animateur registration (Admin is pre-defined)
            if (userRole == Role.ADMIN) {
                throw new IllegalArgumentException("Création de compte Admin interdite.");
            }

            authService.registerUser(nom, email, password, userRole);

            redirectAttributes.addFlashAttribute("succes", "Compte créé avec succès ! Veuillez vous connecter.");
            return "redirect:/auth/login";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erreur", e.getMessage());
            return "redirect:/auth/register";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors de l'inscription.");
            return "redirect:/auth/register";
        }
    }

    // --- HELPER: Redirect Logic ---
    private String redirectBasedOnRole(Personne user) {
        if (user.getRole() == Role.ANIMATEUR) {
            return "redirect:/animateur_page";
        } else if (user.getRole() == Role.PARTICIPANT) {
            return "redirect:/nouvelle-inscription"; // Or /inscriptions/ID
        } else if (user.getRole() == Role.ADMIN) {
            return "redirect:/admin/inscriptions";
        }
        return "redirect:/";
    }
}