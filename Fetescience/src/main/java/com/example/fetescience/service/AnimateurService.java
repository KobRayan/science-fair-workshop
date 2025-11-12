package com.example.fetescience.service;

import com.example.fetescience.model.Animateur;
import com.example.fetescience.repository.AnimateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimateurService {
    private final AnimateurRepository repo;

    public AnimateurSerive(AnimateurRepository repo){
        this.repo=repo;
    }
    public Animateur create(Atelier a) throws RuntimeException {
        return atelierRepository.save(a);
    }

    public void modifierAtelier(Atelier ancien, Atelier nouveau) {
        int index = listeAtelier.indexOf(ancien);
        if (index != -1) {
            listeAtelier.set(index, nouveau);
            nouveau.setAnimateur(this);
        }
    }
    ///CREATE
    public Animateur create(Animateur a) {

        if (a.getTitre() == null || a.getTitre().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty!");
        }

        Optional<Animateur> existing = animateurRepository.findByTitre(a.getTitre());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Title '" + a.getTitre() + "' already exists!");
        }

        return animateurRepository.save(a);
    }
    ///READ ALL
    public Set<Animateur> list() {
        return animateurRepository.findAllBy();}

    ///  READ ONE
    public Animateur getAnimateurById(Long id) {
        return animateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atelier not found with id: " + id));
    }

    /// UPDATE
    public Animateur update(Long id, Animateur animateur) {
        Animateur existingAnimateur = getAnimateurById(id);
        existingAnimateur.setTitre(atelier.getTitre()); // edit l'atelier
        existingAnimateur.setAnimateur(atelier.getAnimateur());
        existingAnimateur.setCreneaux(atelier.getCreneaux());

        return animateurRepository.save(existingAtelier);
    }

    /// DELETE
    public void delete(Long id) {
        try {
            Animateur existingAnimateur = getAnimateurById(id);
            animateurRepository.delete(existingAnimateur);
        } catch (Exception e) {
            throw new RuntimeException(e+" Animateur non trouvé. id cherché : "+id);
        }

    }

}