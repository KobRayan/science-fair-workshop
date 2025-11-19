package com.example.fetescience.service;

import com.example.fetescience.model.Animateur;
import com.example.fetescience.repository.AnimateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimateurService {
    private final AnimateurRepository animateurRepository;

    public AnimateurService(AnimateurRepository animateurRepository){
        this.animateurRepository = animateurRepository;
    }

    public Animateur create(Animateur a) {
        if (a.getTitre() == null || a.getTitre().isEmpty()) {
            throw new IllegalArgumentException("Title/Name cannot be empty!");
        }

        Optional<Animateur> existing = animateurRepository.findByTitre(a.getTitre());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Title/Name '" + a.getTitre() + "' already exists!");
        }

        return animateurRepository.save(a);
    }

    public List<Animateur> listAll() {
        return animateurRepository.findAll();
    }

    public Animateur getAnimateurById(Long id) {
        return animateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animateur not found with id: " + id));
    }

    public Animateur update(Long id, Animateur animateurDetails) {
        Animateur existingAnimateur = getAnimateurById(id);

        existingAnimateur.setTitre(animateurDetails.getTitre());
        return animateurRepository.save(existingAnimateur);
    }

    public void delete(Long id) {
        Animateur existingAnimateur = getAnimateurById(id);
        animateurRepository.delete(existingAnimateur);
    }
}