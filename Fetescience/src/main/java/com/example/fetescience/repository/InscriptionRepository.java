package com.example.fetescience.repository;

import com.example.fetescience.model.Inscription;
import com.example.fetescience.model.Participant;
import com.example.fetescience.model.Atelier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    /**
     * Trouver toutes les inscriptions d'un participant
     */
    List<Inscription> findByParticipant(Participant participant);

    /**
     * Trouver toutes les inscriptions d'un participant par son ID
     */
    List<Inscription> findByParticipantId(Long participantId);

    // -------------------------------------------------------------------------
    //  CORRECTIONS CI-DESSOUS (Navigation via Creneau)
    // -------------------------------------------------------------------------

    /**
     * Vérifier si un participant est déjà inscrit à un atelier (via ses créneaux)
     * Syntaxe JPA : Creneau_Atelier signifie "Le champ atelier DANS l'objet creneau"
     */
    boolean existsByParticipantAndCreneau_Atelier(Participant participant, Atelier atelier);

    /**
     * Trouver toutes les inscriptions pour un atelier donné (tous créneaux confondus)
     */
    List<Inscription> findByCreneau_Atelier(Atelier atelier);

    /**
     * Compter le nombre d'inscriptions pour un participant
     */
    long countByParticipant(Participant participant);
}