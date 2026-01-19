document.addEventListener("DOMContentLoaded", () => {

    const input = document.getElementById("lieu");
    const mapDiv = document.getElementById("map-preview");

    if (!input || !mapDiv) return;

    let map = null;
    let marker = null;

    input.addEventListener("blur", () => {
        const address = input.value.trim();
        if (address.length < 5) return;

        const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(address)}`;

        fetch(url)
            .then(res => res.json())
            .then(data => {
                if (!data || data.length === 0) {
                    mapDiv.innerHTML = "Adresse introuvable";
                    return;
                }

                const lat = data[0].lat;
                const lon = data[0].lon;

                if (!map) {
                    map = L.map(mapDiv).setView([lat, lon], 15);

                    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                        attribution: '© OpenStreetMap'
                    }).addTo(map);

                    marker = L.marker([lat, lon]).addTo(map);
                } else {
                    map.setView([lat, lon], 15);
                    marker.setLatLng([lat, lon]);
                }

                marker.bindPopup(address).openPopup();
            })
            .catch(() => {
                mapDiv.innerHTML = "Erreur lors du chargement de la carte";
            });
    });
});
