# 🎯 Fête de la Science - Gestion des Ateliers

## 📋 Description
Application de gestion des créneaux d'ateliers pour la Fête de la Science 2024.

## 👥 Équipe
- Serigne Mbaye - Maintainer
- Rayan Kobrossly - Maintainer
- Adrien Chaudron - Maintainer
- Zeynab Karim  - Maintainer
- Kassoum KONE - Maintainer
- Lucas Almamy Audin - Maintainer
## 🏗️ Architecture
- **Backend** : Java 21, Spring Boot
- **Frontend** : HTML5, CSS3, JavaScript
- **Base de données** : PostgreSQL
- **API** : REST

## 🚀 Installation
```bash
git clone https://gitlab.univ-lorraine.fr/coll/l-inp/polytech/ia2r-fise-promo-2027/dobrina-boltcheva-science-festival
cd dobrina-boltcheva-science-festival

📋 IMPORTANT : WORKFLOW GIT OBLIGATOIRE
🚨 NE JAMAIS PUSHER DIRECTEMENT SUR MAIN

Pour éviter les conflits et garder un code stable, il est interdit de push directement sur les branches main et develop.
✅ PROCESSUS CORRECT À SUIVRE :

1. Se synchroniser

# Se mettre sur develop
git checkout develop

# Récupérer les derniers changements
git pull origin develop

2. Créer sa branche feature
bash

# Créer une branche pour ta feature
git checkout -b feature/ma-nouvelle-feature

# Exemples :
git checkout -b feature/ajout-classe-creneau
git checkout -b feature/page-inscription-html
git checkout -b feature/correction-bug

3. Coder et tester

    Développer ta feature

    Tester ton code

    Vérifier que ça compile

4. Pousser sa branche
bash

# Ajouter les fichiers modifiés
git add .

# Créer un commit descriptif
git commit -m "feat: ajout de la classe Creneau avec validation"

# Pousser la branche
git push -u origin feature/ma-nouvelle-feature

5. Créer une Merge Request (MR)

    Aller sur GitLab

    Créer une Merge Request de ta branche vers develop

    Demander à 2 coéquipiers de review

    Attendre les validations avant de merger

📝 CONVENTIONS DE COMMITS
bash

git commit -m "feat: ajout fonctionnalité X"
git commit -m "fix: correction bug Y"
git commit -m "docs: mise à jour documentation"
git commit -m "refactor: amélioration code"

🎯 RÉSUMÉ RAPIDE
bash

git checkout develop
git pull origin develop
git checkout -b feature/ma-feature
# → CODER →
git add .
git commit -m "feat: description"
git push -u origin feature/ma-feature
# → CRÉER MR SUR GITLAB →

Respectez ce workflow pour un projet propre et sans conflits ! 🚀


