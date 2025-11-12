package com.example.fetescience.model;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.*;


@Entity
public class Animateur {
    @Getter // pour avoir getId_animateur() direct
    @Id
    private String id_animateur;
    @Getter
    @Column(nullable = false)
    private String nom;

    @OneToMany(mappedBy = "animateur", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Atelier> listeAtelier = new ArrayList<>();

    public Animateur() {}

    public Animateur(String nom){
        this.nom=nom;
    }
    public Animateur(String id_animateur, String nom) {
        this.id_animateur = id_animateur;
        this.nom = nom;
    }

    public void AjouterAtelier(Atelier a) {
        listeAtelier.add(a);
    }

    public void SupprimerAtelier(Atelier a) {
        if (a != null && listeAtelier.contains(a)) {
            listeAtelier.remove(a);
        }
    }
/*
    public void modifierAtelier(Atelier ancien, Atelier nouveau) {
        int index = listeAtelier.indexOf(ancien);
        if (index != -1) {
            listeAtelier.set(index, nouveau);
            nouveau.setAnimateur(this);
        }
    }
*/
    public void AfficherAtelier(Atelier a) {
        System.out.println("Liste des ateliers de l'animateur " + id_animateur + " :");
        for (Atelier atelier : listeAtelier) {
            System.out.println(atelier);
        }
    }

    @Override
    public String toString() {
        return "Animateur{" +
                "id_animateur='" + id_animateur + '\'' +
                ", nbAteliers=" + listeAtelier.size() +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // même object
        if (!(o instanceof Animateur)) return false;
        Animateur animateur = (Animateur) o;
        return this.id_animateur != null && this.id_animateur.equals(animateur.id_animateur);
    }

    @Override
    public int hashCode() {
        return id_animateur != null ? id_animateur.hashCode() : 0;
    }

}