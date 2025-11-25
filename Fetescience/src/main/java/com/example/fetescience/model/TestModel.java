package com.example.fetescience.model;


public class TestModel {

    public static void main(String[] args) {
        System.out.println("=== DÉBUT DU TEST LOCAL DU MODÈLE ===\n");

        // ---------------------------------------------------------
        // 1. CRÉATION DES ANIMATEURS (2 Animateurs)
        // ---------------------------------------------------------
        Animateur anim1 = new Animateur("Sophie L.");
        Animateur anim2 = new Animateur("Marc D.");

        System.out.println("1. Animateurs créés : " + anim1.getNom() + ", " + anim2.getNom());

        // ---------------------------------------------------------
        // 2. CRÉATION DES ATELIERS (2 Ateliers)
        // ---------------------------------------------------------
        Atelier atelier1 = new Atelier("Chimie Amusante");
        Atelier atelier2 = new Atelier("Astronomie pour tous");

        // TEST : Liaison Animateur <-> Atelier
        // On utilise la méthode helper 'ajouterAtelier' qui gère les deux sens
        anim1.ajouterAtelier(atelier1);
        anim2.ajouterAtelier(atelier2);

        // Vérification de la synchro
        System.out.println("\n2. Vérification liens Atelier <-> Animateur :");
        System.out.println("   - Atelier 1 ('" + atelier1.getTitre() + "') est animé par : " + atelier1.getAnimateur().getNom());
        System.out.println("   - Animateur 2 (" + anim2.getNom() + ") possède l'atelier : " + anim2.getListeAtelier().get(0).getTitre());

        // ---------------------------------------------------------
        // 3. CRÉATION DES CRÉNEAUX (3 Créneaux)
        // ---------------------------------------------------------
        // Constructeur : heureDebut, duree, lieu, capacite
        Creneau c1 = new Creneau(9, 60, "Salle A", 2);  // 9h-10h (Capacité 2)
        Creneau c2 = new Creneau(10, 60, "Salle A", 10); // 10h-11h
        Creneau c3 = new Creneau(9, 90, "Salle B", 5);   // 9h-10h30 (Chevauchement avec C1 ?)

        // TEST : Liaison Atelier <-> Créneau
        atelier1.ajouterCreneau(c1); // Chimie à 9h
        atelier1.ajouterCreneau(c2); // Chimie à 10h
        atelier2.ajouterCreneau(c3); // Astro à 9h

        System.out.println("\n3. Créneaux assignés :");
        System.out.println("   - Atelier 1 a " + atelier1.getCreneaux().size() + " créneaux.");
        System.out.println("   - Le créneau C1 est lié à l'atelier : " + c1.getAtelier().getTitre());

        // TEST : Logique de Chevauchement (Overlap)
        System.out.println("\n4. Test logique métier (Chevauchement) :");
        boolean chevauche = c1.chevauche(c3); // C1 (9h-10h) vs C3 (9h-10h30)
        System.out.println("   - C1 chevauche C3 ? " + (chevauche ? "OUI (Correct)" : "NON (Erreur)"));

        boolean chevauche2 = c1.chevauche(c2); // C1 (9h-10h) vs C2 (10h-11h)
        System.out.println("   - C1 chevauche C2 ? " + (chevauche2 ? "OUI (Erreur)" : "NON (Correct)"));

        // ---------------------------------------------------------
        // 4. CRÉATION DES PARTICIPANTS (3 Participants)
        // ---------------------------------------------------------
        Participant p1 = new Participant(); // Pas de nom/email comme demandé
        Participant p2 = new Participant();
        Participant p3 = new Participant();

        // ---------------------------------------------------------
        // 5. INSCRIPTIONS (Test Capacités et Synchro)
        // ---------------------------------------------------------
        System.out.println("\n5. Inscriptions et Capacités :");

        // Inscription P1 -> C1
        p1.addCreneau(c1);
        System.out.println("   - P1 inscrit à C1. Participants dans C1 : " + c1.getParticipants().size());

        // Inscription P2 -> C1
        p2.addCreneau(c1);
        System.out.println("   - P2 inscrit à C1. Participants dans C1 : " + c1.getParticipants().size());

        // Test Capacité (C1 est limité à 2 places)
        if (c1.isComplet()) {
            System.out.println("   - C1 est COMPLET (Correct : 2/2 places prises)");
        } else {
            System.out.println("   - ERREUR : C1 devrait être complet.");
        }

        // Inscription P3 -> C3
        p3.addCreneau(c3);

        // Vérification Bidirectionnelle (Participant voit-il ses créneaux ?)
        System.out.println("\n6. Vérification finale (Vue Participant) :");
        System.out.println("   - P1 a choisi " + p1.getCreneaux().size() + " créneau(x).");
        System.out.println("   - P3 a choisi " + p3.getCreneaux().size() + " créneau(x).");

        // Simulation suppression
        System.out.println("\n7. Test Désinscription :");
        p1.removeCreneau(c1);
        System.out.println("   - P1 se retire de C1.");
        System.out.println("   - C1 est-il complet ? " + (c1.isComplet() ? "OUI" : "NON (Correct, 1 place libérée)"));
        System.out.println("   - Participants restants dans C1 : " + c1.getParticipants().size());

        System.out.println("\n=== FIN DU TEST ===");
    }
}