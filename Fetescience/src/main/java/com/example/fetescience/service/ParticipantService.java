package com.example.fetescience.service;

import com.example.fetescience.model.Inscription;
import com.example.fetescience.model.Participant;
import com.example.fetescience.model.Creneau;
import com.example.fetescience.model.StatutInscription;
import com.example.fetescience.repository.InscriptionRepository;
import com.example.fetescience.repository.ParticipantRepository;
import com.example.fetescience.repository.CreneauRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ParticipantService {

    private final ParticipantRepository participantRepo;
    private final CreneauRepository creneauRepo;
    private final InscriptionRepository inscriptionRepo;

    public ParticipantService(ParticipantRepository pr, CreneauRepository cr, InscriptionRepository ir) {
        this.participantRepo = pr;
        this.creneauRepo = cr;
        this.inscriptionRepo = ir;
    }

    // --- CRUD BASICS ---

    public Participant create(Participant p) {
        return participantRepo.save(p);
    }

    public List<Participant> listAll() {
        return participantRepo.findAll();
    }

    public Participant getById(Long id) {
        return participantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Participant not found with ID: " + id));
    }

    // --- BUSINESS LOGIC (INSCRIPTIONS) ---

    public void inscrire(Long participantId, Long creneauId) {
        Participant p = getById(participantId);
        Creneau c = creneauRepo.findById(creneauId)
                .orElseThrow(() -> new RuntimeException("Creneau not found"));

        // 1. Check if already registered
        boolean alreadyRegistered = p.getInscriptions().stream()
                .anyMatch(i -> i.getCreneau().equals(c));

        if (alreadyRegistered) {
            throw new RuntimeException("Le participant est déjà inscrit à ce créneau !");
        }

        // 2. Check Capacity
        if (c.isComplet()) {
            throw new RuntimeException("Désolé, ce créneau est complet !");
        }

        // 3. Create the Inscription
        Inscription inscription = new Inscription(p, c);
        inscription.setStatut(StatutInscription.VALIDEE);

        // 4. Save (Cascades will handle the lists, but saving explicitly is safer)
        inscriptionRepo.save(inscription);

        // 5. Update Lists (Keep Java objects in sync for this transaction)
        p.getInscriptions().add(inscription);
        c.getInscriptions().add(inscription);

        // 6. Update Creneau Status if full
        if (c.getInscriptions().size() >= c.getCapacite()) {
            c.setStatut(true);
            creneauRepo.save(c);
        }

        participantRepo.save(p);
    }

    public void desinscrire(Long participantId, Long creneauId) {
        Participant p = getById(participantId);
        Creneau c = creneauRepo.findById(creneauId)
                .orElseThrow(() -> new RuntimeException("Creneau not found"));

        // 1. Find the specific Inscription
        Inscription toDelete = p.getInscriptions().stream()
                .filter(i -> i.getCreneau().equals(c))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Inscription introuvable pour ce créneau."));

        // 2. Remove from Java Lists (Memory Sync)
        p.getInscriptions().remove(toDelete);
        c.getInscriptions().remove(toDelete);

        // 3. Delete from Database
        inscriptionRepo.delete(toDelete);

        // 4. Update Creneau Status (It frees up a spot)
        if (c.isStatut()) {
            c.setStatut(false);
            creneauRepo.save(c);
        }

        participantRepo.save(p);
    }


    /*
    private final ParticipantRepository participantRepo;
    private final CreneauRepository creneauRepo;

    public ParticipantService(ParticipantRepository pr, CreneauRepository cr) {
        this.participantRepo = pr;
        this.creneauRepo = cr;
    }

    public Participant create(Participant p) {
        return participantRepo.save(p);
    }

    public List<Participant> listAll() {
        return participantRepo.findAll();
    }

    public Participant getById(Long id) {
        return participantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Participant not found"));
    }

    public void inscrire(Long participantId, Long creneauId) {
        Participant p = getById(participantId);
        Creneau c = creneauRepo.findById(creneauId)
                .orElseThrow(() -> new RuntimeException("Creneau not found"));

        if (c.isComplet()) {
            throw new RuntimeException("Désolé, ce créneau est complet !");
        }

        // Sync logic (handled in model helper, but we save here)
        p.addCreneau(c);

        // If full, mark as full
        if (c.getParticipants().size() >= c.getCapacite()) {
            c.setStatut(true);
        }

        creneauRepo.save(c);
        participantRepo.save(p);
    }

    public void desinscrire(Long participantId, Long creneauId) {
        Participant p = getById(participantId);
        Creneau c = creneauRepo.findById(creneauId)
                .orElseThrow(() -> new RuntimeException("Creneau not found"));

        p.removeCreneau(c);
        c.setStatut(false); // Definitely not full anymore

        creneauRepo.save(c);
        participantRepo.save(p);
    }


     /// Trouver un participant par nom ou le créer s'il n'existe pas

    public Participant findByNomOrCreate(String nom) {
        // Chercher si le participant existe déjà
        Optional<Participant> existing = participantRepo.findByNom(nom);

        if (existing.isPresent()) {
            return existing.get();
        }
        else{
            return null;
        }

        Participant nouveau = new Participant(nom);
        return participantRepo.save(nouveau);
    }

    public Optional<Participant> findById(Long id) {
        return participantRepo.findById(id);
    }*/
}