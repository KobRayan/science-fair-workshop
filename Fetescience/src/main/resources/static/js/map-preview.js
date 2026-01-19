let previewMap = null;
let previewMarker = null;

document.addEventListener("DOMContentLoaded", () => {

    const lieuInput = document.getElementById("lieu");
    const mapDiv = document.getElementById("map-preview");

    if (!lieuInput || !mapDiv) return;

    lieuInput.addEventListener("blur", () => {
        const address = lieuInput.value.trim();
        if (!address) return;

        const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(address)}`;

        fetch(url, {
            headers: {
                "Accept": "application/json",
                "User-Agent": "fetescience-app"
            }
        })
            .then(res => res.json())
            .then(data => {

                if (!data || data.length === 0) {
                    mapDiv.innerHTML = "❌ Adresse introuvable";
                    mapDiv.style.color = "red";
                    return;
                }

                const lat = data[0].lat;
                const lon = data[0].lon;

                if (!previewMap) {
                    previewMap = L.map(mapDiv).setView([lat, lon], 15);

                    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                        attribution: '© OpenStreetMap'
                    }).addTo(previewMap);

                    previewMarker = L.marker([lat, lon]).addTo(previewMap);
                } else {
                    previewMap.setView([lat, lon], 15);
                    previewMarker.setLatLng([lat, lon]);
                }

                previewMarker.bindPopup(address).openPopup();
            })
            .catch(() => {
                mapDiv.innerHTML = "❌ Erreur lors du chargement de la carte";
            });
    });
});
