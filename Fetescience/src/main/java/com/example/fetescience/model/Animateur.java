package com.example.fetescience.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Animateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "animateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atelier> listeAtelier = new ArrayList<>();

    public Animateur() {}

    public Animateur(String nom) {
        this.nom = nom;
    }

    public void ajouterAtelier(Atelier a) {
        listeAtelier.add(a);
        a.setAnimateur(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // même object
        if (!(o instanceof Animateur)) return false;
        Animateur animateur = (Animateur) o;
        return this.id != null && this.id.equals(animateur.id);
    }

    /*@Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }*/

    public String getNom(){
        return nom;
    }

}