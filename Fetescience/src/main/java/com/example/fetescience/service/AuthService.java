package com.example.fetescience.service;

import com.example.fetescience.model.Animateur;
import com.example.fetescience.model.Participant;
import com.example.fetescience.model.Personne;
import com.example.fetescience.model.Role;
import com.example.fetescience.repository.ParticipantRepository;
import com.example.fetescience.repository.AnimateurRepository;
import com.example.fetescience.repository.PersonneRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final PersonneRepository personneRepository;
    private final ParticipantRepository participantRepository;
    private final AnimateurRepository animateurRepository;

    public AuthService(PersonneRepository personneRepository,
                       ParticipantRepository participantRepository,
                       AnimateurRepository animateurRepository) {
        this.personneRepository = personneRepository;
        this.participantRepository = participantRepository;
        this.animateurRepository = animateurRepository;
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

    // ✅ MODIFIED: Register ANY type of user (except Admin)
    public Personne registerUser(String nom, String email, String password, Role role) {
        // 1. Check if email exists
        if (personneRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Cet email est déjà utilisé.");
        }

        // 2. Create based on Role
        if (role == Role.PARTICIPANT) {
            Participant p = new Participant(nom, email, password);
            return participantRepository.save(p);
        }
        else if (role == Role.ANIMATEUR) {
            Animateur a = new Animateur(nom, email, password);
            return animateurRepository.save(a);
        }
        else {
            throw new IllegalArgumentException("Type de compte non autorisé.");
        }
    }
}