package com.example.fetescience.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Home";
    }

    // SUPPRIMÉ : la méthode nouvelleInscription()
    // Car elle est maintenant dans InscriptionController
}