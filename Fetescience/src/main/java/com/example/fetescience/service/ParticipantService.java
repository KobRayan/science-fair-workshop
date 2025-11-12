package com.example.fetescience.service;

import com.example.fetescience.model.Participant;
import com.example.fetescience.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService {
    private final ParticipantRepository repo;
    public ParticipantService(repo){
        this.repo=repo;
    }
    public void inscrire(Creneau c) {
        if (!choix.contains(c)) {
            choix.add(c);
            System.out.println(id_participant + " inscrit à l’atelier " + c.getAtelier().getTitre() + " sur le créneau " + c);
        } else {
            System.out.println("Déjà inscrit à ce créneau !");
        }
    }

    public void desinscrire(Creneau c) {
        if (choix.contains(c)) {
            choix.remove(c);
            System.out.println(id_participant + " désinscrit de " + c.getAtelier().getTitre());
        } else {
            System.out.println("Non inscrit à ce créneau.");
        }
}