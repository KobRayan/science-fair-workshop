package com.example.fetescience.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Animateur extends Personne {

    @OneToMany(mappedBy = "animateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Atelier> ateliers = new HashSet<>();

    public Animateur() {
        super();
        this.setRole(Role.ANIMATEUR);
    }

    public Animateur(String nom, String email, String password) {
        super(nom, email, password, Role.ANIMATEUR);
    }

    public void ajouterAtelier(Atelier atelier) {
        ateliers.add(atelier);
        atelier.setAnimateur(this);
    }

    public void retirerAtelier(Atelier atelier) {
        ateliers.remove(atelier);
        atelier.setAnimateur(null);
    }
}