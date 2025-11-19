package com.example.fetescience.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Creneau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int horaireDebut;
    private int duree;
    private String lieu;
    private boolean statut; // true = complet
    private int capacite;

    @ManyToOne
    @JoinColumn(name = "atelier_id")
    private Atelier atelier;

    @ManyToMany
    @JoinTable(
            name = "creneau_participant",
            joinColumns = @JoinColumn(name = "creneau_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private Set<Participant> participants = new HashSet<>();

    public Creneau() {}

    public Creneau(int horaireDebut, int duree, String lieu, int capacite) {
        this.horaireDebut = horaireDebut;
        this.duree = duree;
        this.lieu = lieu;
        this.capacite = capacite;
        this.statut = false;
    }

    // Check if full
    public boolean isComplet() {
        return participants.size() >= capacite;
    }

    // Check overlap
    public boolean chevauche(Creneau autre) {
        int finThis = this.horaireDebut * 60 + this.duree;
        int debutThis = this.horaireDebut * 60;
        int finAutre = autre.horaireDebut * 60 + autre.duree;
        int debutAutre = autre.horaireDebut * 60;
        return (debutThis < finAutre) && (finThis > debutAutre);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Creneau creneau = (Creneau) o;
        return this.id != null && this.id.equals(creneau.id);
    }

   /* @Override
    public int hashCode() {
        return Objects.hash(id);
    }*/

}
