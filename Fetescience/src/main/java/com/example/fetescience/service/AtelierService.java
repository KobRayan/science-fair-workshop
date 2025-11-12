package com.example.fetescience.service;

import com.example.fetescience.model.Atelier;
import com.example.fetescience.repository.AtelierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

///  CRUD (Create Read Update Delete)

@Service
public class AtelierService {
    private final AtelierRepository atelierRepository;
    public AtelierService(AtelierRepository atelierRepository) { this.atelierRepository = atelierRepository; }

    /// CREATE
    // needs throw catch
   // public Atelier create(Atelier a) throws RuntimeException { return atelierRepository.save(a); }
    public Atelier create(Atelier a) {

        if (a.getTitre() == null || a.getTitre().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty!");
        }

        Optional<Atelier> existing = atelierRepository.findByTitre(a.getTitre());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Title '" + a.getTitre() + "' already exists!");
        }

        return atelierRepository.save(a);
    }

    /// READ ALL
    public Set<Atelier> list() { return atelierRepository.findAllBy();}

    ///  READ ONE
    public Atelier getAtelierById(Long id) {
        return atelierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atelier not found with id: " + id));
    }

    /// UPDATE
    public Atelier update(Long id, Atelier atelier) {
        Atelier existingAtelier = getAtelierById(id);
        existingAtelier.setTitre(atelier.getTitre()); // edit l'atelier
        existingAtelier.setAnimateur(atelier.getAnimateur());
        existingAtelier.setCreneaux(atelier.getCreneaux());

        return atelierRepository.save(existingAtelier);
    }

    /// DELETE
    public void delete(Long id) {
        try {
            Atelier existingAtelier = getAtelierById(id);
            atelierRepository.delete(existingAtelier);
        } catch (Exception e) {
            throw new RuntimeException(e+" Atelier non trouvé. id cherché : "+id);
        }

    }





}
