package com.example.fetescience.repository;

import com.example.fetescience.model.Creneau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CreneauRepository extends JpaRepository<Creneau, Long> {}
