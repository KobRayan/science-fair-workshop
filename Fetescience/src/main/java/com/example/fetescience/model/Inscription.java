package com.example.fetescience.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
/**
 * Modèle représentant une inscription d'un participant à un créneau d'atelier
 * 
 * Relations :
 * - @ManyToOne vers Participant (un participant peut avoir plusieurs inscriptions)
 * - @ManyToOne vers Creneau (un créneau peut avoir plusieurs inscriptions)
 * - @ManyToOne vers Atelier (pour faciliter les requêtes)
 */
@Entity
@Getter
@Setter
public class Inscription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Le participant inscrit
     */
    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant;
    
    /**
     * Le créneau réservé
     */
    @ManyToOne
    @JoinColumn(name = "creneau_id", nullable = false)
    private Creneau creneau;
    
    /**
     * L'atelier (dénormalisé pour faciliter les requêtes)
     */
    @ManyToOne
    @JoinColumn(name = "atelier_id", nullable = false)
    private Atelier atelier;
    
    /**
     * Date et heure de l'inscription
     */
    @Column(nullable = false)
    private LocalDateTime dateInscription;
    
    /**
     * Statut de l'inscription
     * Valeurs possibles : EN_ATTENTE, VALIDEE, REFUSEE
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutInscription statut;
    
    /**
     * Constructeur par défaut (requis par JPA)
     */
    public Inscription() {
        this.dateInscription = LocalDateTime.now();
        this.statut = StatutInscription.EN_ATTENTE;
    }
    
    /**
     * Constructeur avec paramètres
     */
    public Inscription(Participant participant, Creneau creneau, Atelier atelier) {
        this();
        this.participant = participant;
        this.creneau = creneau;
        this.atelier = atelier;
    }
    
    /**
     * Vérifie si l'inscription peut être annulée
     * (au moins 2 jours avant le créneau)
     */
    public boolean peutEtreAnnulee() {
        // Pour simplifier, on retourne true
        // À améliorer avec la vraie logique de date
        return true;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inscription)) return false;
        Inscription that = (Inscription) o;
        return this.id != null && this.id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
