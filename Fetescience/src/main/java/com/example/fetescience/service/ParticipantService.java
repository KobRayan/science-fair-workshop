package com.example.fetescience.service;

import com.example.fetescience.model.Participant;
import com.example.fetescience.model.Creneau;
import com.example.fetescience.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public void inscrire(Participant p, Creneau c) {
        Participant participant = getParticipantById(p.getId());

        Set<Creneau> creneauxChoisis = participant.getCreneaux();

        if (!creneauxChoisis.contains(c)) {
            creneauxChoisis.add(c);
            participant.setCreneaux(creneauxChoisis);
            participantRepository.save(participant);
            System.out.println(participant.getNom() + " inscrit à l’atelier " + c.getAtelier().getTitre() + " sur le créneau " + c);
        } else {
            System.out.println(participant.getNom() + " est déjà inscrit à ce créneau !");
        }
    }

    public void desinscrire(Long participantId, Creneau c) {
        Participant participant = getParticipantById(participantId);
        Set<Creneau> creneauxChoisis = participant.getCreneaux();

        if (creneauxChoisis.contains(c)) {
            creneauxChoisis.remove(c);
            participant.setCreneaux(creneauxChoisis);
            participantRepository.save(participant);
            System.out.println(participant.getNom() + " désinscrit de l'atelier " + c.getAtelier().getTitre());
        } else {
            System.out.println(participant.getNom() + " n'est pas inscrit à ce créneau.");
        }
    }

    public Participant create(Participant p) {
        if (p.getNom() == null || p.getNom().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }

        return participantRepository.save(p);
    }

    public List<Participant> listAll() {
        return participantRepository.findAll();
    }

    public Participant getParticipantById(Long id) {
        return participantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participant not found with id: " + id));
    }

    public Participant update(Long id, Participant participantDetails) {
        Participant existingParticipant = getParticipantById(id);

        existingParticipant.setNom(participantDetails.getNom());
        existingParticipant.setEmail(participantDetails.getEmail());

        return participantRepository.save(existingParticipant);
    }

    public void delete(Long id) {
        Participant existingParticipant = getParticipantById(id);
        participantRepository.delete(existingParticipant);
    }
}