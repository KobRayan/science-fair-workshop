package com.example.fetescience.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Animateur extends Personne{


    @OneToMany(mappedBy = "animateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atelier> listeAtelier = new ArrayList<>();

    public Animateur() {
        super();
        this.setRole(Role.ANIMATEUR);
    }

    public Animateur(String nom, String email, String password) {
        super(nom, email, password, Role.ANIMATEUR);
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
        return this.getId() != null && this.getId().equals(animateur.getId());
    }

    /*@Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }*/



}