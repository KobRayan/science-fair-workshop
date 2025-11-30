-- Participant de test
INSERT INTO participant(nom) VALUES ('Jean Dupont');

-- Animateurs
INSERT INTO animateur(nom) VALUES ('Dr. Kone');
INSERT INTO animateur(nom) VALUES ('Prof. Kobrossly');

-- Ateliers
INSERT INTO atelier(titre, animateur_id) VALUES ('Cybersécurité', 2);
INSERT INTO atelier(titre, animateur_id) VALUES ('Intelligence Artificielle', 1);

-- Créneaux pour Cybersécurité
INSERT INTO creneau(horaire_debut, duree, lieu, capacite, statut, atelier_id)
VALUES (10, 60, 'LaBRI, Talence', 15, false, 1);

INSERT INTO creneau(horaire_debut, duree, lieu, capacite, statut, atelier_id)
VALUES (14, 60, 'LaBRI, Talence', 15, false, 1);

-- Créneaux pour IA
INSERT INTO creneau(horaire_debut, duree, lieu, capacite, statut, atelier_id)
VALUES (9, 90, 'Polytech Nancy', 20, false, 2);