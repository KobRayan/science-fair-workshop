package com.example.fetescience.service;

import com.example.fetescience.model.Animateur;
import com.example.fetescience.repository.AnimateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AnimateurService {
    private final AnimateurRepository repo;

    public AnimateurService(AnimateurRepository repo){
        this.repo = repo;
    }

    public Animateur create(Animateur a) {
        if (a.getNom() == null || a.getNom().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        if (repo.findByNom(a.getNom()).isPresent()) {
            throw new IllegalArgumentException("Animateur exists!");
        }
        return repo.save(a);
    }

    public List<Animateur> listAll() {
        return repo.findAll();
    }

    public Animateur getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Animateur not found"));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}