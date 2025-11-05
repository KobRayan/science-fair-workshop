package com.example.fetescience.model;
import jakarta.persistence.*;


@Entity
public class Animateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id_animateur;
    @Column(nullable = false);
    private String nom;
    ArrayList<Atelier> listeAtelier = new ArrayList<Atelier>;

    public Animateur(String id_animateur, String nom) {
        this.id_animateur = id_animateur;
        this.nom = nom;
    }

    public void AjouterAtelier(Atelier a) {
        return listeAtelier.add(a);
    }

    public void SupprimerAtelier(Atelier a) {
        if (a != null && listeAtelier.contains(a)) {
            listeAtelier.remove(a);
        }
    }

    public void modifierAtelier(Atelier ancien, Atelier nouveau) {
        int index = listeAtelier.indexOf(ancien);
        if (index != -1) {
            listeAtelier.set(index, nouveau);
            nouveau.setAnimateur(this);
        }
    }

    public void AfficherAtelier(Atelier a) {
        System.out.println("Liste des ateliers de l'animateur " + id_animateur + " :");
        for (Atelier a : listeAtelier) {
            System.out.println(a);
        }
    }

    @Override
    public String toString() {
        return "Animateur{" +
                "id_animateur='" + id_animateur + '\'' +
                ", nbAteliers=" + listeAtelier.size() +
                '}';
    }

    public String getNom() {
        return nom;
    }
    public String getId_animateur(){
        return id_animateur;
    }
}