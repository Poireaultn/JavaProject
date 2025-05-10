package modele;

import java.time.LocalDate;

public class BonAchat {
    private int identifiantBon;
    private double prix;
    private String categorieProduit;
    private LocalDate dateExpiration;
    private int coutPoints;

    public BonAchat(int identifiantBon, double prix, String categorieProduit, LocalDate dateExpiration, int coutPoints) {
        this.identifiantBon = identifiantBon;
        this.prix = prix;
        this.categorieProduit = categorieProduit;
        this.dateExpiration = dateExpiration;
        this.coutPoints = coutPoints;
    }

    public int getIdentifiantBon() {
        return identifiantBon;
    }

    public double getPrix() {
        return prix;
    }

    public String getCategorieProduit() {
        return categorieProduit;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public int getCoutPoints() {
        return coutPoints;
    }
}
