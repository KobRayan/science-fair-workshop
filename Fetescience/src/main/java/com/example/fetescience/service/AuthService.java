package com.example.fetescience.service;

import com.example.fetescience.model.Personne;
import com.example.fetescience.repository.PersonneRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final PersonneRepository personneRepository;

    public AuthService(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    public Personne authenticate(String email, String password) {
        // 1. Find user by email
        Optional<Personne> user = personneRepository.findByEmail(email);

        // 2. Check if user exists and password matches
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get(); // Login Success
        }

        return null; // Login Failed
    }
}