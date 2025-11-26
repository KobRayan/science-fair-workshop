package com.example.fetescience.controller;

import com.example.fetescience.model.Personne;
import com.example.fetescience.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Personne login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Personne user = authService.authenticate(email, password);

        if (user != null) {
            return user; // Returns the user object (with role, id, name)
        } else {
            throw new RuntimeException("Invalid login credentials");
        }
    }
}