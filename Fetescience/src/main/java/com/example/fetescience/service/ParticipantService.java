package com.example.fetescience.service;

import com.example.fetescience.model.Participant;
import com.example.fetescience.model.Creneau;
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

    /**
     * Trouver un participant par nom ou le créer s'il n'existe pas
     */
    public Participant findByNomOrCreate(String nom) {
        // Chercher si le participant existe déjà
        Optional<Participant> existing = participantRepo.findByNom(nom);

        if (existing.isPresent()) {
            return existing.get();
        }

        // Sinon, créer un nouveau participant
        Participant nouveau = new Participant(nom);
        return participantRepo.save(nouveau);
    }

    public Optional<Participant> findById(Long id) {
        return participantRepo.findById(id);
    }
}