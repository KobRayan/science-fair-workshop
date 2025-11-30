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
        // 1. Trouver le participant
        Participant participant = participantRepo.findById(participantId)
                .orElseThrow(() -> new IllegalArgumentException("Participant introuvable"));

        // 2. Trouver l'atelier par son titre
        Atelier atelier = atelierRepo.findByTitre(titreAtelier)
                .orElseThrow(() -> new IllegalArgumentException("Atelier '" + titreAtelier + "' introuvable"));

        // 3. Trouver le premier créneau disponible de cet atelier
        List<Creneau> creneaux = creneauRepo.findByAtelierId(atelier.getId());
        
        if (creneaux.isEmpty()) {
            throw new IllegalArgumentException("Aucun créneau disponible pour cet atelier");
        }

        Creneau creneau = creneaux.get(0); // Prendre le premier créneau

        // 4. Vérifier que le créneau n'est pas complet
        if (creneau.isComplet()) {
            throw new IllegalArgumentException("Ce créneau est complet");
        }

        // 5. Créer l'inscription
        Inscription inscription = new Inscription(participant, creneau, atelier);
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
}
