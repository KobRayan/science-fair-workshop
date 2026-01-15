async function chargerCreneaux() {
    const atelierId = document.getElementById("atelierSelect").value;
    const creneauSelect = document.getElementById("creneauId");
    const creneauGroup = document.getElementById("creneau-group");
    const btnSubmit = document.getElementById("btn-submit");

    creneauSelect.innerHTML = '';
    btnSubmit.disabled = true; // Reset to disabled

    if(!atelierId) {
        creneauGroup.style.display = 'none';
        return;
    }

    creneauGroup.style.display = 'block';

    try {
        const response = await fetch(`/api/ateliers/${atelierId}/creneaux`);
        const creneaux = await response.json();

        let hasAvailableSlot = false;

        creneaux.forEach(c => {
            const opt = document.createElement("option");
            opt.value = c.id;

            if(c.statut) { // Assuming statut means "COMPLET"/Full
                opt.text = c.horaireDebut + "h (COMPLET)";
                opt.disabled = true;
            } else {
                opt.text = c.horaireDebut + "h";
                hasAvailableSlot = true; // Found at least one open slot
            }

            creneauSelect.add(opt);
        });

        // ✅Enable button immediately if there is a valid choice
        // The browser automatically selects the first non-disabled option.
        if (hasAvailableSlot && creneauSelect.value) {
            btnSubmit.disabled = false;
        }

        // Keep onchange in case they switch to a different slot
        creneauSelect.onchange = () => {
            btnSubmit.disabled = false;
        };

    } catch (error) {
        console.error("Erreur chargement créneaux", error);
    }
}