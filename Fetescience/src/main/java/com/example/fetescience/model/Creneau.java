@Entity
public class Creneau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // identifiant de la table

    private int horaire_debut;     // date + heure de début
    private int duree;          // durée du créneau
    private String lieu;             // lieu du créneau
    private boolean statut ; // libre ou occupé
    private int capacite;

    // Lien avec l’atelier
    @ManyToOne
    @JoinColumn(name = "atelier_id")
    private Atelier atelier;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "creneau_participant",
            joinColumns = @JoinColumn(name = "creneau_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private List<Participant> participants; // liste des participants

    //  Constructeur vide
    public Creneau() {
        this.participants = new ArrayList<>();
    }

    // Constructeur d’un créneau libre
    public Creneau(int horaire_debut, int duree, String lieu, int capacite) {
        this.horaire_debut = horaire_debut;
        this.duree = duree;
        this.lieu = lieu;
        this.capacite = capacite;
        this.participants = new ArrayList<>();
        this.statut = false;  // libre au départ
    }

    //Constructeur d’un créneau déjà occupé
    public Creneau(int horaire_debut, int duree, String lieu, int capacite, Participant participant) {
        this(horaire_debut, duree, lieu, capacite); // appelle l’autre constructeur
        this.participants.add(participant);
        this.statut = true; // devient occupé
    }



    public int getHoraire_debut() {
        return horaire_debut;
    }

    public void setHoraire_debut(int horaire_debut) {
        this.horaire_debut = horaire_debut;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "Créneau{" +
                "horaire_debut=" + horaire_debut +
                ", durée=" + duree +
                ", lieu='" + lieu + '\'' +
                ", capacité=" + capacite +
                ", statut=" + (statut ? "occupé" : "libre") +
                ", participants=" + participants.size() +
                '}';
    }

    // Ajoute un participant si le créneau n’est pas plein
    public void occuper(Participant p) {
        // TODO : ajouter le participant et mettre à jour le statut si nécessaire
    }

    // Retire un participant et met le créneau libre si aucun participant restant
    public void liberer(Participant p) {
        // TODO : retirer le participant et mettre à jour le statut
    }

    // Vérifie si ce créneau chevauche un autre créneau
    public boolean chevauche(Creneau autre) {
        // TODO : comparer les horaires pour détecter un chevauchement
        //
        return false; // valeur par défaut pour la compilation
    }


}