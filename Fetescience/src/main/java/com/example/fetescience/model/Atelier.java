package com.example.fetescience.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Atelier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @ManyToOne
    @JoinColumn(name = "animateur_id")
    private Animateur animateur;

    @OneToMany(mappedBy = "atelier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Creneau> creneaux = new ArrayList<>();

    public Atelier() {}

    public Atelier(String titre) {
        this.titre = titre;
    }

    public void ajouterCreneau(Creneau c) {
        creneaux.add(c);
        c.setAtelier(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // même object
        if (!(o instanceof Atelier)) return false;
        Atelier atelier = (Atelier) o;
        return this.id != null && this.id.equals(atelier.id);
    }

    /*@Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }*/
}