package com.example.fetescience.service;

import com.example.fetescience.model.Atelier;
import com.example.fetescience.model.Creneau;
import com.example.fetescience.model.Participant;
import com.example.fetescience.repository.CreneauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
public class CreneauService {

    @Autowired
    private CreneauRepository creneauRepository;

    //Créer un créneau
    @Override
    public Creneau creerCreneau(Creneau creneau) {
        return creneauRepository.save(creneau);
    }
    // Lister tous les créneaux
    @Override
    public List<Creneau> listerCreneaux() {
        return creneauRepository.findAll();
    }

    // Affichage des créneaux d’un participant
    public List<Creneau> listerCreneauxParParticipant(Participant participant) {
        // Tri par horaire_debut croissant
        return creneauRepository.findByParticipantsContainingOrderByHoraireDebutAsc(participant);
    }

    // Ajouter un créneau à un atelier
    public Creneau ajouterCreneauAtelier(Atelier atelier, Creneau creneau) {
        // Vérifier chevauchement avec les créneaux existants
        for (Creneau c : atelier.getCreneaux()) {
            if (c.chevauche(creneau)) {
                throw new RuntimeException("Chevauchement avec un autre créneau");
            }
        }
        creneau.setAtelier(atelier);
        atelier.getCreneaux().add(creneau);
        return creneauRepository.save(creneau);
    }

    // Recherche d’un créneau libre à une heure donnée
    //Cette méthode retourne la liste des ateliers qui ont au moins un créneau libre à une heure donnée
    public List<Atelier> ateliersAvecCreneauLibre(int horaireRecherche) {
        List<Creneau> creneaux = creneauRepository.findByHoraireDebutLessThanEqualAndStatutFalse(horaireRecherche);
        //Le résultat ici est une liste de créneaux libres qui pourraient correspondre à l’heure recherchée.
        Set<Atelier> ateliers = new HashSet<>(); // On crée un Set pour stocker les ateliers.
        for (Creneau c : creneaux) {
            ateliers.add(c.getAtelier());
        }
        return new ArrayList<>(ateliers);
    }

    // Sert à inscrire un participant à un créneau et enregistrer cette inscription dans la base
    public void occuperCreneau(Creneau creneau, Participant participant) {
        creneau.occuper(participant);
        creneauRepository.save(creneau);
    }

    // Sert à désinscrire un participant d’un créneau et mettre à jour l’état du créneau dans la base.
    public void libererCreneau(Creneau creneau, Participant participant) {
        creneau.liberer(participant);
        creneauRepository.save(creneau);
    }





}