package com.example.fetescience.controller;

import com.example.fetescience.model.Participant;
import com.example.fetescience.service.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ParticipantController {

    private final ParticipantService participantService;

    // Injection du SERVICE (pas du repository)
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    // GET http://localhost:8081/participants
    @GetMapping("/participants")
    public List<Participant> getAllParticipants() {
        return participantService.listAll();
    }

    // GET http://localhost:8081/participants/{id}
    @GetMapping("/participants/{id}")
    public ResponseEntity<Participant> getParticipantById(@PathVariable Long id) {
        // Utilise la méthode findById (Optional) du service
        return participantService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}