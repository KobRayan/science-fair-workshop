package com.example.fetescience.service;

import com.example.fetescience.model.Atelier;
import com.example.fetescience.repository.AtelierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtelierService {
    private final AtelierRepository repo;
    public AtelierService(AtelierRepository repo) { this.repo = repo; }

    public List<Atelier> list() { return repo.findAll(); }
    public Atelier create(Atelier a) { return repo.save(a); }
}
