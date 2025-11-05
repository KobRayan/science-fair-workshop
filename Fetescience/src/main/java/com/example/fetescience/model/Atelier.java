package com.example.fetescience.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Atelier {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_atelier;

    @Column(nullable = false)
    private String titre;

    private Animateur animateur = new Animateur();
    private Participant participant = new Participant();
    private List<Creneau> creneaux;


    public Atelier() {}
    public Atelier(String titre) {
        this.titre = titre;
        creneaux = new ArrayList<Creneau>();
    }


    public Long getId() { return id_atelier; }
    public String getTitre() { return titre; }
    public List<Creneau> getCreneaux() { return creneaux;}


    public void setTitre(String titre) { this.titre = titre; }
    public void ajouterCreneau(Creneau c){creneaux.add(c);}
    public void supprimerCreneau(int index){creneaux.remove(index);}
    public void supprimerCreneau(Creneau c){creneaux.remove(c);}
    public void modifierStatutCreneau(int index, boolean statut){creneaux.get(index).setStatut(statut);}
    public void modifierlieu(int index, String lieu){creneaux.get(index).setLieu(lieu);}
    public void modifierduree(int index, int duree){creneaux.get(index).setDuree(duree);}
    public void modifierhoraire(int index, int horaire){creneaux.get(index).setLieu(lieu);}
    public Crenau getCreneaux(int index){return Creneaux;}

    public void ajouterParticipant(Participant p){this.participant=p;}
    public boolean verifiercompatibilite(int index, Participant part){return false;}
    public Participant getParticipant(){return participant;}

    public void ajouterAnimateur(Animateur a){this.animateur = a;}
    public Animateur getAnimateur(){return animateur;}

    public String toString(){
        String message = "C'est un atelier\n"+ "ID : "+id_atelier+"\n"+ "Titre : "+titre+"\n"+"Animateur : "+animateur.getNom()+"\n"
                + "Creneaux : \n"+"   ";

        Iterator<Creneau> iterator = creneaux.iterator();
        while (iterator.hasNext()) {
            Creneau c = iterator.next();
            message += c.toString();
            message += "\n     ";
        }
        return message;
    }
}