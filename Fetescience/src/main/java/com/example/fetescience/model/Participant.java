package com.example.fetescience.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom; // <--- Added field

    @ManyToMany(mappedBy = "participants")
    private Set<Creneau> creneaux = new HashSet<>();

    public Participant() {}

    // Constructor with name
    public Participant(String nom) {
        this.nom = nom;
    }

    // Helper methods for synchronization
    public void addCreneau(Creneau c) {
        this.creneaux.add(c);
        c.getParticipants().add(this);
    }

    public void removeCreneau(Creneau c) {
        this.creneaux.remove(c);
        c.getParticipants().remove(this);
    }

@Override
public boolean equals(Object o) {
    if (this == o) return true;  // même object
    if (!(o instanceof Participant)) return false;
    Participant participant = (Participant) o;
    return this.id != null && this.id.equals(participant.id);
}

/*@Override
public int hashCode() {
    return id != null ? id.hashCode() : 0;
}*/
}
