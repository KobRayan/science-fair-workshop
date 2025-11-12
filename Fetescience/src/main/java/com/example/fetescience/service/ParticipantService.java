package com.example.fetescience.service;

import com.example.fetescience.model.Participant;
import com.example.fetescience.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService {
    private final ParticipantRepository repo;
    public ParticipantService(repo){
        this.repo=repo;
    }
    public void inscrire(Creneau c) {
        if (!choix.contains(c)) {
            choix.add(c);
            System.out.println(id_participant + " inscrit à l’atelier " + c.getAtelier().getTitre() + " sur le créneau " + c);
        } else {
            System.out.println("Déjà inscrit à ce créneau !");
        }
    }

    public void desinscrire(Creneau c) {
        if (choix.contains(c)) {
            choix.remove(c);
            System.out.println(id_participant + " désinscrit de " + c.getAtelier().getTitre());
        } else {
            System.out.println("Non inscrit à ce créneau.");
        }
        /// CREATE
        // needs throw catch
        // public Atelier create(Atelier a) throws RuntimeException { return atelierRepository.save(a); }
        public Participant create(Participant a) {

            if (a.getTitre() == null || a.getTitre().isEmpty()) {
                throw new IllegalArgumentException("Title cannot be empty!");
            }

            Optional<Participant> existing = participantRepository.findByTitre(a.getTitre());
            if (existing.isPresent()) {
                throw new IllegalArgumentException("Title '"
                        + a.getTitre() + "' already exists!");
            }

            return participantRepository.save(a);
        }

        /// READ ALL
        public Set<Participant> list() {
            return participantRepository.findAllBy();
        }

        ///  READ ONE
        public Participant getParticipantById(Long id) {
            return participantRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Participant not found with id: " + id));
        }

        /// UPDATE
        public Participant update(Long id, Participant participant) {
            Participant existingParticipant = getParticpantById(id);
            existingParticipant.setTitre(participant.getTitre()); // edit l'atelier
            existingParticipant.setAnimateur(participant.getAnimateur());
            existingParticipant.setCreneaux(participant.getCreneaux());

            return participantRepository.save(existingParticipant);
        }

        /// DELETE
        public void delete(Long id) {
            try {
                Participant existingParticipant = getParticipantById(id);
                participantRepository.delete(existingParticipant);
            } catch (Exception e) {
                throw new RuntimeException(e+" Participant non trouvé. id cherché : "+id);
            }

        }
}