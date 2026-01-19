document.addEventListener("DOMContentLoaded", () => {

    document.querySelectorAll(".map").forEach((mapDiv) => {

        const address = mapDiv.dataset.address;

        if (!address) {
            mapDiv.innerHTML = "📍 Adresse non renseignée";
            return;
        }

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
                    mapDiv.innerHTML = "📍 Adresse introuvable";
                    return;
                }

                const lat = data[0].lat;
                const lon = data[0].lon;

                const map = L.map(mapDiv).setView([lat, lon], 15);

                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                    attribution: '© OpenStreetMap'
                }).addTo(map);

                L.marker([lat, lon])
                    .addTo(map)
                    .bindPopup(address)
                    .openPopup();
            })
            .catch(() => {
                mapDiv.innerHTML = "❌ Erreur de chargement de la carte";
            });
    });
});
