/*package com.example.fetescience;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FetescienceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FetescienceApplication.class, args);
    }
    //System.out.println("Hello guys");
    // test de push
}
//HELLOO*/
package com.example.fetescience;

import com.example.fetescience.model.*;
import com.example.fetescience.service.*;
import com.example.fetescience.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FetescienceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FetescienceApplication.class, args);
    }

    @Bean
    public CommandLineRunner testServices(AnimateurService animateurService,
                                          AtelierService atelierService,
                                          CreneauService creneauService,
                                          ParticipantService participantService,
                                          ParticipantRepository participantRepo,
                                          CreneauRepository creneauRepo,
                                          AtelierRepository atelierRepo,
                                          AnimateurRepository animateurRepo,
                                          InscriptionRepository inscriptionRepo) {
        return (args) -> {
            System.out.println("🧹 NETTOYAGE DE LA BASE DE DONNÉES...");

            // ⚠️ ORDRE IMPORTANT : supprimer d'abord les inscriptions !
            inscriptionRepo.deleteAll();
            creneauRepo.deleteAll();
            participantRepo.deleteAll();
            atelierRepo.deleteAll();
            animateurRepo.deleteAll();

            System.out.println("✨ Base vide. Début de l'insertion...");
            System.out.println("\n⚡⚡⚡ DÉBUT DU TEST INTÉGRATION ⚡⚡⚡\n");

            // 1. ANIMATEURS
            Animateur anim1 = animateurService.create(new Animateur("Marie Curie"));
            Animateur anim2 = animateurService.create(new Animateur("Albert Einstein"));

            // 2. ATELIERS
            Atelier atelier1 = new Atelier("Physique Quantique");
            atelier1.setAnimateur(anim1);
            atelierService.create(atelier1);

            Atelier atelier2 = new Atelier("Relativité");
            atelier2.setAnimateur(anim2);
            atelierService.create(atelier2);

            // 3. CRENEAUX
            Creneau c1 = new Creneau(10, 60, "Amphi A", 2);
            creneauService.addCreneauToAtelier(atelier1, c1);

            // 4. PARTICIPANTS
            System.out.println("--- Création des Participants ---");
            Participant p1 = participantService.create(new Participant("Alice"));
            System.out.println("✅ Participant créé : " + p1.getNom() + " (ID=" + p1.getId() + ")");

            // 5. INSCRIPTIONS
            participantService.inscrire(p1.getId(), c1.getId());

            System.out.println("\n✨✨✨ TEST TERMINÉ ✨✨✨");
            System.out.println("➡️ Application prête sur http://localhost:8081");
            System.out.println("➡️ Page admin : http://localhost:8081/admin/inscriptions");
        };
    }
}