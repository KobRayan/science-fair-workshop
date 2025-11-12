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
    public void modifierAtelier(Atelier ancien, Atelier nouveau) {
        int index = listeAtelier.indexOf(ancien);
        if (index != -1) {
            listeAtelier.set(index, nouveau);
            nouveau.setAnimateur(this);
        }
    }
}