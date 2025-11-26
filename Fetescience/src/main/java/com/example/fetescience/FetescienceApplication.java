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

import java.util.List;

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
                                          AnimateurRepository animateurRepo) {
        return (args) -> {
            System.out.println("🧹 NETTOYAGE DE LA BASE DE DONNÉES...");
            // Order is important because of Foreign Keys!
            // Delete children first (Creneau/Participant), then Parents (Atelier/Animateur)
            creneauRepo.deleteAll();
            participantRepo.deleteAll();
            atelierRepo.deleteAll();
            animateurRepo.deleteAll();
            System.out.println("✨ Base vide. Début de l'insertion...");
            System.out.println("\n⚡⚡⚡ DÉBUT DU TEST INTÉGRATION (AVEC NOMS) ⚡⚡⚡\n");

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

            // 4. PARTICIPANTS (Maintenant avec des Noms !)
            System.out.println("--- Création des Participants ---");
            Participant p1 = participantService.create(new Participant("Alice"));
            System.out.println("Helllo");
            //Participant p2 = participantService.create(new Participant("Bob"));
           // Participant p3 = participantService.create(new Participant("Charlie"));

            System.out.println("✅ Participants créés : " + p1.getNom());

            // 5. INSCRIPTIONS
            participantService.inscrire(p1.getId(), c1.getId());
          //  participantService.inscrire(p2.getId(), c1.getId());

            System.out.println("\n✨✨✨ TEST TERMINÉ ✨✨✨");
            System.out.println("➡️ Vérifiez les noms sur http://localhost:8081/h2");
        };
    }
}