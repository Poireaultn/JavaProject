package modele;

public class CentreDeTri {
    private int identifiantCentre;
    private String nom;
    private String adresse;

    public CentreDeTri(int identifiantCentre, String nom, String adresse) {
        this.identifiantCentre = identifiantCentre;
        this.nom = nom;
        this.adresse = adresse;
    }

    public int getIdentifiantCentre() {
        return identifiantCentre;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }
}
