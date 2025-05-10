package modele;

public class Poubelle {
    private int identifiantPoubelle;
    private String ville;
    private String quartier;
    private String typePoubelle;
    private int capacite;
    private double poidsActuel; 


    public Poubelle(int identifiantPoubelle, String ville, String quartier, String typePoubelle, int capacite, double poidsActuel) {
        this.identifiantPoubelle = identifiantPoubelle;
        this.ville = ville;
        this.quartier = quartier;
        this.typePoubelle = typePoubelle;
        this.capacite = capacite;
        this.poidsActuel = poidsActuel;
    }

    public int getIdentifiantPoubelle() {
        return identifiantPoubelle;
    }

    public String getVille() {
        return ville;
    }

    public String getQuartier() {
        return quartier;
    }

    public String getTypePoubelle() {
        return typePoubelle;
    }

    public int getCapacite() {
        return capacite;
    }

    public double getPoidsActuel() {
        return poidsActuel;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public void setPoidsActuel(double poidsActuel) {
        this.poidsActuel = poidsActuel;
    }
}
