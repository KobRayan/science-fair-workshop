/**
 * Script de gestion des désinscriptions aux ateliers
 * Répond aux questions II.2.1 et II.3.1 du sujet
 */

// Constantes
const JOURS_LIMITE_DESINSCRIPTION = 2;
const TEL_CNRS = "01 400 400";
const URL_DESINSCRIPTION = "http://www.serveur.fr/desinscription/";

/**
 * Calcule le nombre de jours entre aujourd'hui et une date donnée
 * @param {string} dateStr - Date au format YYYY-MM-DD
 * @returns {number} Nombre de jours
 */
function joursAvantEvenement(dateStr) {
    const dateEvenement = new Date(dateStr);
    const aujourdhui = new Date();

    // Réinitialiser les heures pour comparer uniquement les dates
    dateEvenement.setHours(0, 0, 0, 0);
    aujourdhui.setHours(0, 0, 0, 0);

    const differenceMs = dateEvenement - aujourdhui;
    const differenceJours = Math.ceil(differenceMs / (1000 * 60 * 60 * 24));

    return differenceJours;
}

/**
 * Remplace le bouton de désinscription par un message d'avertissement
 * si l'atelier a lieu dans moins de 2 jours
 * @param {HTMLElement} inscriptionDiv - L'élément div de l'inscription
 */
function gererBoutonDesinscription(inscriptionDiv) {
    const dateAtelier = inscriptionDiv.getAttribute('data-date');
    const bouton = inscriptionDiv.querySelector('.btn-desinscription');

    if (!dateAtelier || !bouton) {
        console.warn('Date ou bouton manquant pour une inscription');
        return;
    }

    const joursRestants = joursAvantEvenement(dateAtelier);

    // Si moins de 2 jours, remplacer le bouton par un message
    if (joursRestants < JOURS_LIMITE_DESINSCRIPTION) {
        const messageDiv = document.createElement('div');
        messageDiv.className = 'message-telephoner';
        messageDiv.textContent = `Appelez le numéro ${TEL_CNRS} pour vous désinscrire`;

        bouton.parentNode.replaceChild(messageDiv, bouton);
    }
}

/**
 * Envoie une requête de désinscription au serveur
 * @param {string} idInscription - L'identifiant de l'inscription
 * @returns {Promise<boolean>} True si la désinscription a réussi
 */
async function desinscriptionAtelier(idInscription) {
    try {
        const url = URL_DESINSCRIPTION + idInscription;
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        return response.status === 200;
    } catch (error) {
        console.error('Erreur lors de la désinscription:', error);
        return false;
    }
}

/**
 * Supprime l'élément d'inscription du DOM
 * @param {HTMLElement} inscriptionDiv - L'élément à supprimer
 */
function supprimerInscriptionDuDOM(inscriptionDiv) {
    // Supprimer le <hr> suivant s'il existe
    const hrSuivant = inscriptionDiv.nextElementSibling;
    if (hrSuivant && hrSuivant.tagName === 'HR') {
        hrSuivant.remove();
    }

    // Ajouter une animation de sortie
    inscriptionDiv.style.transition = 'opacity 0.3s ease-out';
    inscriptionDiv.style.opacity = '0';

    setTimeout(() => {
        inscriptionDiv.remove();

        // Vérifier s'il reste des inscriptions
        const inscriptionsRestantes = document.querySelectorAll('.inscription');
        if (inscriptionsRestantes.length === 0) {
            const conteneur = document.getElementById('inscription-list');
            conteneur.innerHTML = '<p class="aucune-inscription">Vous n\'avez aucune inscription.</p>';
        }
    }, 300);
}

/**
 * Gère le clic sur un bouton de désinscription
 * @param {Event} event - L'événement de clic
 */
async function handleDesinscription(event) {
    const bouton = event.target;
    const inscriptionDiv = bouton.closest('.inscription');
    const idInscription = inscriptionDiv.getAttribute('id-inscription');

    if (!idInscription) {
        alert('Erreur : identifiant d\'inscription introuvable');
        return;
    }

    // Désactiver le bouton pendant le traitement
    bouton.disabled = true;
    bouton.textContent = 'Désinscription en cours...';

    // Envoyer la requête de désinscription
    const succes = await desinscriptionAtelier(idInscription);

    if (succes) {
        supprimerInscriptionDuDOM(inscriptionDiv);
    } else {
        // Réactiver le bouton en cas d'échec
        bouton.disabled = false;
        bouton.textContent = 'Se désinscrire';
        alert('La suppression n\'a pas été réalisée. Veuillez réessayer ou contacter le service.');
    }
}

/**
 * Initialisation au chargement de la page
 */
function initialiser() {
    // Traiter tous les boutons de désinscription
    const inscriptions = document.querySelectorAll('.inscription');

    inscriptions.forEach(inscriptionDiv => {
        // Vérifier et modifier les boutons selon la date
        gererBoutonDesinscription(inscriptionDiv);

        // Ajouter l'écouteur d'événement sur les boutons restants
        const bouton = inscriptionDiv.querySelector('.btn-desinscription');
        if (bouton) {
            bouton.addEventListener('click', handleDesinscription);
        }
    });
}

// Lancer l'initialisation quand le DOM est chargé
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initialiser);
} else {
    initialiser();
}