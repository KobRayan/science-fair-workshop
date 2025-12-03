package com.example.fetescience.service;

import com.example.fetescience.model.*;
import com.example.fetescience.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InscriptionService {

    private final InscriptionRepository inscriptionRepo;
    private final ParticipantRepository participantRepo;
    private final AtelierRepository atelierRepo;
    private final CreneauRepository creneauRepo;

    public InscriptionService(InscriptionRepository inscriptionRepo,
                            ParticipantRepository participantRepo,
                            AtelierRepository atelierRepo,
                            CreneauRepository creneauRepo) {
        this.inscriptionRepo = inscriptionRepo;
        this.participantRepo = participantRepo;
        this.atelierRepo = atelierRepo;
        this.creneauRepo = creneauRepo;
    }

    public Inscription create(Inscription inscription) {
        return inscriptionRepo.save(inscription);
    }

    public List<Inscription> findAll() {
        return inscriptionRepo.findAll();
    }

    public Optional<Inscription> findById(Long id) {
        return inscriptionRepo.findById(id);
    }

    public Inscription update(Inscription inscription) {
        return inscriptionRepo.save(inscription);
    }

    public List<Inscription> findByParticipantId(Long participantId) {
        return inscriptionRepo.findByParticipantId(participantId);
    }

    /**
     * Créer une inscription à partir d'un titre d'atelier
     */
    public Inscription creerInscription(Long participantId, String titreAtelier) {

        Participant participant = participantRepo.findById(participantId)
                .orElseThrow(() -> new IllegalArgumentException("Participant introuvable"));

        Atelier atelier = atelierRepo.findByTitre(titreAtelier)
                .orElseThrow(() -> new IllegalArgumentException("Atelier '" + titreAtelier + "' introuvable"));

        // Sorted by date
        List<Creneau> creneaux =
                creneauRepo.findByAtelierIdOrderByHoraireDebutAsc(atelier.getId());

        if (creneaux.isEmpty()) {
            throw new IllegalArgumentException("Aucun créneau disponible pour cet atelier");
        }

        // Find earliest non-full creneau
        Creneau creneauDisponible = null;

        for (Creneau c : creneaux) {
            if (!c.isComplet()) {
                creneauDisponible = c;
                break;
            }
        }

        if (creneauDisponible == null) {
            throw new IllegalArgumentException("Tous les créneaux sont complets pour cet atelier");
        }

        // Shared checks
        verifierInscriptionPossible(participantId, creneauDisponible);

        // Create inscription
        Inscription inscription = new Inscription(participant, creneauDisponible);

        return inscriptionRepo.save(inscription);
    }

    /**
    * Inscrit le participant depuis un creneau disponbile (l'utilisateur selectionne son créneau)
    */

public Inscription creerInscription(Long participantId, Long creneauId) {

    Participant participant = participantRepo.findById(participantId)
            .orElseThrow(() -> new IllegalArgumentException("Participant introuvable"));

    Creneau creneau = creneauRepo.findById(creneauId)
            .orElseThrow(() -> new IllegalArgumentException("Creneau introuvable"));

    // Reuse shared validations
    verifierInscriptionPossible(participantId, creneau);

    // Create inscription
    Inscription inscription = new Inscription(participant, creneau);

    return inscriptionRepo.save(inscription);
}


    /**
     * Récupérer les inscriptions d'un participant
     */
    public List<Inscription> getInscriptionsByParticipant(Long participantId) {
        return inscriptionRepo.findByParticipantId(participantId);
    }

    /**
     * Supprimer une inscription
     */
    public boolean supprimerInscription(Long inscriptionId, Long participantId) {
        Optional<Inscription> inscriptionOpt = inscriptionRepo.findById(inscriptionId);
        
        if (inscriptionOpt.isEmpty()) {
            return false;
        }

        Inscription inscription = inscriptionOpt.get();
        
        // Vérifier que l'inscription appartient bien au participant
        if (!inscription.getParticipant().getId().equals(participantId)) {
            throw new IllegalArgumentException("Cette inscription ne vous appartient pas");
        }

        // Vérifier si l'inscription peut être annulée
        if (!inscription.peutEtreAnnulee()) {
            throw new IllegalArgumentException("Cette inscription ne peut plus être annulée");
        }

        inscriptionRepo.delete(inscription);
        return true;
    }


    ///  private helper
    private void verifierInscriptionPossible(Long participantId, Creneau creneau) {

        // Participant already exists (should be checked before calling)
        if (creneau.isComplet()) {
            throw new IllegalArgumentException("Ce créneau est complet");
        }

        // Check duplicate: participant already in THIS creneau
        boolean dejaInscrit = inscriptionRepo
                .existsByParticipantIdAndCreneauId(participantId, creneau.getId());

        if (dejaInscrit) {
            throw new IllegalArgumentException("Participant déjà inscrit à ce créneau");
        }
    }

}
