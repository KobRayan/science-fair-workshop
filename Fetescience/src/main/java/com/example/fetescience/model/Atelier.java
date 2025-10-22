package com.example.fetescience.model;

import jakarta.persistence.*;

@Entity
public class Atelier {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    public Atelier() {}
    public Atelier(String titre) { this.titre = titre; }

    public Long getId() { return id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
}