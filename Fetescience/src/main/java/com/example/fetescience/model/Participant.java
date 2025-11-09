package com.example.fetescience.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
public class Participant {
    @Id
    @Column(nullable = false)
    private String id_participant;
    @ManyToMany
    private Set<Creneau> choix = new HashSet<>(); /// a revoir les many to many et les autres annotations

    public Participant(String id_participant) {
        this.id_participant = id_participant;
    }

    public Participant() {
    }

    /*public void inscrire(Atelier a, Creneau c) {
        if (!choix.contains(c)) {
            choix.add(c);
            //a.ajouterParticipant(this,c); // essayons une autre facon

            System.out.println(idAnimateur + " inscrit à l’atelier " + a.getNom() + " sur le créneau " + c);
        } else {
            System.out.println("Déjà inscrit à ce créneau !");
        }
    }*/

    /// //// **************** peut etre comme ca car atelier depuis creneau?
    public void inscrire(Creneau c) {
        if (!choix.contains(c)) {
            choix.add(c);
            c.occuper(this);

            System.out.println(id_participant + " inscrit à l’atelier " + c.getAtelier().getTitre() + " sur le créneau " + c);
        } else {
            System.out.println("Déjà inscrit à ce créneau !");
        }
    }


  /*  public void desinscrire(Atelier a, Creneau c) {

        if (choix.remove(c)) {
            a.retirerParticipant(this, c);
            System.out.println(id_animateur + " désinscrit de " + a.getNom());
        } else {
            System.out.println("Non inscrit à ce créneau.");
        }
    }*/

    /// ********************** alternative ??
    public void desinscrire(Creneau c) {

        if (choix.contains(c)) {
            c.liberer(this);
            choix.remove(c);
            System.out.println(id_participant + " désinscrit de " + c.getAtelier().getTitre());
        } else {
            System.out.println("Non inscrit à ce créneau.");
        }


    }


    @Override
    public String toString() {
        return "Id_participant : " + id_participant + "choix : " + choix;
    }

    public String afficherChoix() {
        return "Liste des créneaux : " + choix;
    }
}
