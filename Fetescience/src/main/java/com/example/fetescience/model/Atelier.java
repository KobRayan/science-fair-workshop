package com.example.fetescience.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
public class Atelier {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_atelier;

    @Column(nullable = false)
    private String titre;

    @Getter //easier syntax
    @Setter
    @ManyToOne
    @JoinColumn(name = "animateur_id_animateur")
    private Animateur animateur;

    //private Participant participant;

    @Setter
    @Getter
    @OneToMany(mappedBy = "atelier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Creneau> creneaux;


    public Atelier() {creneaux = new ArrayList<Creneau>();}
    public Atelier(String titre) {
        this.titre = titre;
        creneaux = new ArrayList<Creneau>();
    }


    public Long getId() { System.out.println(id_atelier); return id_atelier; }
    public String getTitre() {System.out.println(getTitre());   return titre; }
    public List<Creneau> getCreneaux() {System.out.println(creneaux);  return creneaux;}

    // **************************************************
    // VERIFIER QUE LA LISTE N'EST PAS VIDE AVEC TRY ET CATCH ET THROWS PLUS TARD
    // **************************************************
    public void setTitre(String titre) { System.out.println("title set"); this.titre = titre; }

    // ******************************* CRENEAUX
    public void ajouterCreneau(Creneau c){System.out.println("creneaux set"); creneaux.add(c);}

    public void supprimerCreneau(int index){if (index >= 0 && index < creneaux.size()){ System.out.println("creneaux deleted"); creneaux.remove(index);}}
    public void supprimerCreneau(Creneau c){ if (c != null && creneaux.contains(c)){System.out.println("creneaux deleted"); creneaux.remove(c);}}

    public void modifierStatutCreneau(int index, boolean statut){if (index >= 0 && index < creneaux.size()){System.out.println("creneaux modifie");creneaux.get(index).setStatut(statut);}}
    public void modifierlieu(int index, String lieu){if(index >= 0 && index < creneaux.size() && creneaux.get(index)!=null){System.out.println("creneaux modifie"); creneaux.get(index).setLieu(lieu);}}
    public void modifierduree(int index, int duree){if(index >= 0 && index < creneaux.size() && creneaux.get(index)!=null){System.out.println("creneaux modifie"); creneaux.get(index).setDuree(duree);}}
    public void modifierhoraire(int index, int horaire){if(index >= 0 && index < creneaux.size() && creneaux.get(index)!=null){System.out.println("creneaux modifie"); creneaux.get(index).setHoraire_debut(horaire);}}

    public Creneau getCreneaux(int index) {
        if (index >= 0 && index < creneaux.size()) {
            System.out.println(creneaux.get(index));
            return creneaux.get(index);
        } else {
            return null;
        }
    }
    //public List<Creneau> getListCreneaux(){return creneaux;}


    // ********************************** PARTICIPANT
    //public void ajouterParticipant(Participant p){this.participant=p;}
   // public boolean verifiercompatibilite(int index, Participant part){return false;} /* ou Creneau c chepa au cas ou

    // ************************ Participant should be in creneaux not in atelier !!!!!!!

    //public Participant getParticipant(){return participant;}

    public void ajouterAnimateur(Animateur a){this.animateur = a;}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // même object
        if (!(o instanceof Atelier)) return false;
        Atelier atelier = (Atelier) o;
        return this.id_atelier != null && this.id_atelier.equals(atelier.id_atelier);
    }

    @Override
    public int hashCode() {
        return id_atelier != null ? id_atelier.hashCode() : 0;
    }
}