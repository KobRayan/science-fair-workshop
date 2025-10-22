package com.example.fetescience.controller;

import com.example.fetescience.model.Atelier;
import com.example.fetescience.repository.AtelierRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ateliers")
public class AtelierController {

    private final AtelierRepository repo;

    public AtelierController(AtelierRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Atelier> all() {
        return repo.findAll();
    }

    @PostMapping
    public Atelier create(@RequestBody Atelier a) {
        return repo.save(a);
    }
}
