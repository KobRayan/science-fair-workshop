package com.example.fetescience.model;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Participant{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private String id_animateur ;
    @ManytoMany
    private Listcreneau<creneau> choix = new HashSet<>();

public Participant ( String id_animateur){
    this.id_animateur=id_animateur;
}
public void inscrire(Atelier a, Creneau c){
    if (!choix.contains(c)) {
        choix.add(c);
        a.ajouterParticipant(this,c);
        System.out.println(idAnimateur + " inscrit à l’atelier " + a.getNom() + " sur le créneau " + c);
    } else {
        System.out.println("Déjà inscrit à ce créneau !");
    }
}
public void desinscrire(Atelier a, Creneau c ){
    public void desinscrire(Atelier a, Creneau c) {
        if (choix.remove(c)) {
            a.retirerParticipant(this, c);
            System.out.println(idAnimateur + " désinscrit de " + a.getNom() );
        } else {
            System.out.println("Non inscrit à ce créneau.");
        }
    }

}
@Override
public String toString(){
    return "Id_animateur : " + id_animateur + "choix : " + choix;
}

public String Afficherchoix(){
    System.out.println("Liste des creneaux " + choix + " :");
    for (Creneau c : choix)
        System.out.println(c);
    }
}
}