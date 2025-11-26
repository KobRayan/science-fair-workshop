package com.example.fetescience.controller;

import com.example.fetescience.model.Participant;
import com.example.fetescience.repository.ParticipantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ParticipantController {

    private final ParticipantRepository participantRepository;

    public ParticipantController(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    // GET http://localhost:8081/participants
    @GetMapping("/participants")
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    // GET http://localhost:8081/participants/{id}
    @GetMapping("/participants/{id}")
    public ResponseEntity<Participant> getParticipantById(@PathVariable Long id) {
        return participantRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
