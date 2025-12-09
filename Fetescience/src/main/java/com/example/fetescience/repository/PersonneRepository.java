package com.example.fetescience.repository;

import com.example.fetescience.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PersonneRepository extends JpaRepository<Personne, Long> {
    // This works for BOTH Animateurs and Participants
    Optional<Personne> findByEmail(String email);

    // Optional: Find by email and password (simple login)
    Optional<Personne> findByEmailAndPassword(String email, String password);
}