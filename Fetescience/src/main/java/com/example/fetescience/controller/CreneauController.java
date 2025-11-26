package com.example.fetescience.controller;

import com.example.fetescience.model.Creneau;
import com.example.fetescience.model.Participant;
import com.example.fetescience.repository.CreneauRepository;
import com.example.fetescience.repository.ParticipantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CreneauController {

    private final CreneauRepository creneauRepository;
    private final ParticipantRepository participantRepository;

    public CreneauController(CreneauRepository creneauRepository,
                             ParticipantRepository participantRepository) {
        this.creneauRepository = creneauRepository;
        this.participantRepository = participantRepository;
    }

    // GET http://localhost:8081/creneaux
    @GetMapping("/creneaux")
    public List<Creneau> getAllCreneaux() {
        return creneauRepository.findAll();
    }

    // GET http://localhost:8081/creneaux/{id}
    @GetMapping("/creneaux/{id}")
    public ResponseEntity<Creneau> getCreneauById(@PathVariable Long id) {
        return creneauRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET http://localhost:8081/creneaux/disponibles?horaire=10
    @GetMapping("/creneaux/disponibles")
    public List<Creneau> getCreneauxDisponibles(@RequestParam int horaire) {
        return creneauRepository
                .findByHoraireDebutLessThanEqualAndStatutFalse(horaire);
    }

    // GET http://localhost:8081/participants/1/creneaux
    @GetMapping("/participants/{idParticipant}/creneaux")
    public ResponseEntity<List<Creneau>> getCreneauxPourParticipant(
            @PathVariable Long idParticipant) {

        return participantRepository.findById(idParticipant)
                .map(p -> ResponseEntity.ok(
                        creneauRepository
                                .findByParticipantsContainingOrderByHoraireDebutAsc(p)
                ))
                .orElse(ResponseEntity.notFound().build());
    }
}
