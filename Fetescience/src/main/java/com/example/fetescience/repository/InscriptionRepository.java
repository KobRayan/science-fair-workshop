package com.example.fetescience.repository;

import com.example.fetescience.model.Inscription;
import com.example.fetescience.model.Participant;
import com.example.fetescience.model.Atelier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour gérer les inscriptions
 */
@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    
    /**
     * Trouver toutes les inscriptions d'un participant
     * @param participant Le participant
     * @return Liste des inscriptions
     */
    List<Inscription> findByParticipant(Participant participant);
    
    /**
     * Trouver toutes les inscriptions d'un participant par son ID
     * @param participantId L'ID du participant
     * @return Liste des inscriptions
     */
    List<Inscription> findByParticipantId(Long participantId);
    
    /**
     * Vérifier si un participant est déjà inscrit à un atelier
     * @param participant Le participant
     * @param atelier L'atelier
     * @return true si déjà inscrit, false sinon
     */
    boolean existsByParticipantAndAtelier(Participant participant, Atelier atelier);
    
    /**
     * Trouver toutes les inscriptions pour un atelier
     * @param atelier L'atelier
     * @return Liste des inscriptions
     */
    List<Inscription> findByAtelier(Atelier atelier);
    
    /**
     * Compter le nombre d'inscriptions pour un participant
     * @param participant Le participant
     * @return Le nombre d'inscriptions
     */
    long countByParticipant(Participant participant);
}
