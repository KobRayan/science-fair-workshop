package com.example.fetescience.controller;

import com.example.fetescience.model.Atelier;
import com.example.fetescience.repository.AtelierRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AtelierController {

    private final AtelierRepository atelierRepository;

    public AtelierController(AtelierRepository atelierRepository) {
        this.atelierRepository = atelierRepository;
    }

    // GET http://localhost:8081/ateliers
    @GetMapping("/ateliers")
    public List<Atelier> getAllAteliers() {
        return atelierRepository.findAll();
    }

    // GET http://localhost:8081/ateliers/1
    @GetMapping("/ateliers/{id}")
    public ResponseEntity<Atelier> getAtelierById(@PathVariable Long id) {
        return atelierRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET http://localhost:8081/ateliers/search?titre=Robotique
    @GetMapping("/ateliers/search")
    public ResponseEntity<Atelier> getAtelierByTitre(@RequestParam String titre) {
        return atelierRepository.findByTitre(titre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
