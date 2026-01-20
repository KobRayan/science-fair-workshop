/* =========================
   MAP MANAGER (UNIQUE)
   ========================= */

const mapsRegistry = new Map();

/**
 * Affiche ou met à jour une carte Leaflet
 * @param {HTMLElement} container - div de la map
 * @param {string} adresse - adresse à géocoder
 * @param {number} zoom - niveau de zoom
 */
function afficherCarte({ container, adresse, zoom = 15 }) {
    if (!container || !adresse) return;

    fetch(`/api/geocode?address=${encodeURIComponent(adresse)}`)
        .then(res => res.json())
        .then(data => {
            if (!data || data.length === 0) {
                container.innerHTML = "📍 Adresse introuvable";
                return;
            }

            const lat = data[0].lat;
            const lon = data[0].lon;

            let mapData = mapsRegistry.get(container);

            if (!mapData) {
                const map = L.map(container).setView([lat, lon], zoom);

                L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
                    attribution: "© OpenStreetMap"
                }).addTo(map);

                const marker = L.marker([lat, lon]).addTo(map);

                mapsRegistry.set(container, { map, marker });

                // IMPORTANT : recalcul taille si cachée avant
                setTimeout(() => map.invalidateSize(), 200);
            } else {
                mapData.map.setView([lat, lon], zoom);
                mapData.marker.setLatLng([lat, lon]);
            }

            mapsRegistry.get(container).marker
                .bindPopup(adresse)
                .openPopup();
        })
        .catch(() => {
            container.innerHTML = "❌ Erreur de chargement de la carte";
        });
}

/* =========================
   1) MAPS STATIQUES
   ========================= */
document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".map").forEach(div => {
        afficherCarte({
            container: div,
            adresse: div.dataset.address
        });
    });
});

/* =========================
   2) PREVIEW ADRESSE
   ========================= */
document.getElementById("lieu")?.addEventListener("blur", e => {
    afficherCarte({
        container: document.getElementById("map-preview"),
        adresse: e.target.value
    });
});

/* =========================
   3) MAP CRENEAU
   ========================= */
function afficherMapCreneau(adresse) {
    afficherCarte({
        container: document.getElementById("map-creneau"),
        adresse
    });
}

function openGoogleMaps(address) {
    if (!address) return;
    window.open(
        `https://www.google.com/maps/dir/?api=1&destination=${encodeURIComponent(address)}`,
        "_blank"
    );
}

function openGoogleMapsFromCurrentLocation(destinationAddress) {
    if (!destinationAddress) {
        alert("Adresse non renseignée");
        return;
    }

    if (!navigator.geolocation) {
        alert("La géolocalisation n'est pas supportée par votre navigateur.");
        return;
    }

    navigator.geolocation.getCurrentPosition(
        position => {
            const lat = position.coords.latitude;
            const lon = position.coords.longitude;

            const url = `https://www.google.com/maps/dir/?api=1&origin=${lat},${lon}&destination=${encodeURIComponent(destinationAddress)}`;
            window.open(url, "_blank");
        },
        () => {
            alert("Impossible de récupérer votre position.");
        }
    );
}