package com.example.fetescience.repository;

import com.example.fetescience.model.Atelier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtelierRepository extends JpaRepository<Atelier, Long> {
    Optional<Atelier> findByTitre(String titre);
}
