package com.example.fetescience.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Entity
public class Creneau {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //identifiant de la table

    private int horaire_debut;     // date + heure de début
    private int duree;          // durée du créneau
    private String lieu;             // lieu du créneau
    private boolean statut ; // libre ou occupé
    private int capacite;

    // Lien avec l’atelier
    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "atelier_id")
    private Atelier atelier;

    @Getter
    @ManyToMany
    @JoinTable(
            name = "creneau_participant",
            joinColumns = @JoinColumn(name = "creneau_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private Set<Participant> participants;
    ///private List<Participant> participants; // liste des participants /// on passe à un Set pour eviter les doublons de participants

    //  Constructeur vide
    //public Creneau() { this.participants = new ArrayList<>(); }
    public Creneau() { this.participants = new HashSet<>();}

    // Constructeur d’un créneau libre
    public Creneau(int horaire_debut, int duree, String lieu, int capacite) {
        this.horaire_debut = horaire_debut;
        this.duree = duree;
        this.lieu = lieu;
        this.capacite = capacite;
      //  this.participants = new ArrayList<>();
        this.participants = new HashSet<>();
        this.statut = false;  // libre au départ
    }

    //Constructeur d’un créneau déjà occupé
    public Creneau(int horaire_debut, int duree, String lieu, int capacite, Participant participant) {
        this(horaire_debut, duree, lieu, capacite); // appelle l’autre constructeur
        this.participants = new HashSet<>();
        this.participants.add(participant); /// il faut avoir instancié le set
        this.statut = true; // devient occupé
    }



    public int getHoraire_debut() {
        return horaire_debut;
    }

    public void setHoraire_debut(int horaire_debut) {
        this.horaire_debut = horaire_debut;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
        for(Participant p  : participants){
            p.setCreneau(this);
        }
    }

    @Override
    public String toString() {
        return "Créneau{" +
                "horaire_debut=" + horaire_debut +
                ", durée=" + duree +
                ", lieu='" + lieu + '\'' +
                ", capacité=" + capacite +
                ", statut=" + (statut ? "occupé" : "libre") +
                ", participants=" + participants.size() +
                '}';
    }

    // Ajoute un participant si le créneau n’est pas plein
    public void occuper(Participant p) {
        // TODO : ajouter le participant et mettre à jour le statut si nécessaire
    }

    // Retire un participant et met le créneau libre si aucun participant restant
    public void liberer(Participant p) {
        // TODO : retirer le participant et mettre à jour le statut
    }

    // Vérifie si ce créneau chevauche un autre créneau
    public boolean chevauche(Creneau autre) {
        // TODO : comparer les horaires pour détecter un chevauchement
        //
        return false; // valeur par défaut pour la compilation
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Creneau creneau = (Creneau) o;
        return this.id != null && this.id.equals(creneau.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
