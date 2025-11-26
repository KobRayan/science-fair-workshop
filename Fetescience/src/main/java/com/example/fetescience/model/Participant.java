package com.example.fetescience.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Participant extends Personne{

    @ManyToMany(mappedBy = "participants")
    private Set<Creneau> creneaux = new HashSet<>();

    public Participant() {
        super();
        this.setRole(Role.PARTICIPANT);
    }

    public Participant(String nom, String email, String password) {
        super(nom, email, password, Role.PARTICIPANT);
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
    return this.getId() != null && this.getId().equals(participant.getId());
}

/*@Override
public int hashCode() {
    return id != null ? id.hashCode() : 0;
}*/
}
