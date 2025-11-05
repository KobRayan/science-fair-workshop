package com.example.fetescience.model;
import jakarta.persistence.*;

public class Creneau {
    private int horaire_debut;
    private String lieu;
    private int duree;
    private boolean statut;
    private Participant participant;

    // Constructeur vide
    public Creneau() {}

    // Constructeur complet
    public Creneau(int horaire_debut,int duree) {
        this.horaire_debut = horaire_debut;
        this.duree = duree;

    }

    // Getters et Setters
    public int getHoraire_debut() {
        return horaire_debut;
    }

    public void setHoraire_debut(int horaire_debut) {
        this.horaire_debut = horaire_debut;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    // Méthode toString()
    @Override
    public String toString() {
        return "Creneau : [ " +
                "horaire_debut=" + horaire_debut +
                ", duree=" + duree + " ]";
    }