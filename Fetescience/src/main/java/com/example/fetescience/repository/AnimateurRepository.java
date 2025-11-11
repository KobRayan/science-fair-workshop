package com.example.fetescience.repository;

import com.example.fetescience.model.Animateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AnimateurRepository extends JpaRepository<Animateur, String> {}
