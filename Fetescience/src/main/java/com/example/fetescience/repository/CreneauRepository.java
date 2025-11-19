package com.example.fetescience.repository;

import com.example.fetescience.model.Creneau;
import com.example.fetescience.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CreneauRepository extends JpaRepository<Creneau, Long> {
    List<Creneau> findByParticipantsContainingOrderByHoraireDebutAsc(Participant participant);
    List<Creneau> findByHoraireDebutLessThanEqualAndStatutFalse(int horaire);
}