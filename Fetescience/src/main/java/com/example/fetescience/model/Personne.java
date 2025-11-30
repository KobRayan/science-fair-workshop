package com.example.fetescience.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Creates a main table for shared info
@Getter @Setter
public abstract class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Column(unique = true) // Email must be unique for login
    @NotBlank(message = "L'email est obligatoire") // Stops empty strings ""
    @Email(message = "Format d'email invalide")    // Stops "marie-at-science.com"
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password; // In a real app, this should be hashed!

    @Enumerated(EnumType.STRING) // Stores "ANIMATEUR" as text in DB
    private Role role; // "ANIMATEUR" or "PARTICIPANT"

    public Personne() {}

    public Personne(String nom, String email, String password, Role role) {
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}