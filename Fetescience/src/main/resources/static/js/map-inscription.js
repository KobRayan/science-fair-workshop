let map = null;
let marker = null;

function afficherMapCreneau(adresse) {
    if (!adresse) return;

    const mapDiv = document.getElementById("map-creneau");
    if (!mapDiv) return;

    fetch(`https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(adresse)}`)
        .then(res => res.json())
        .then(data => {
            if (!data || data.length === 0) {
                mapDiv.innerHTML = "📍 Adresse introuvable";
                return;
            }

            const lat = data[0].lat;
            const lon = data[0].lon;

            if (!map) {
                map = L.map("map-creneau").setView([lat, lon], 15);

                L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
                    attribution: "© OpenStreetMap"
                }).addTo(map);

                marker = L.marker([lat, lon]).addTo(map);
            } else {
                map.setView([lat, lon], 15);
                marker.setLatLng([lat, lon]);
            }

            marker.bindPopup(adresse).openPopup();
        })
        .catch(() => {
            mapDiv.innerHTML = "❌ Erreur de chargement de la carte";
        });
}
