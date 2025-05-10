package modele;

public class Dechet {
    private int identifiantDechet;
    private String typeDechet;
    private double poids;
    private int idCorbeille;

    public Dechet(String typeDechet, double poids, int idCorbeille) {
        this.typeDechet = typeDechet;
        this.poids = poids;
        this.idCorbeille = idCorbeille;
    }

    public Dechet(int identifiantDechet, String typeDechet, double poids, int idCorbeille) {
        this.identifiantDechet = identifiantDechet;
        this.typeDechet = typeDechet;
        this.poids = poids;
        this.idCorbeille = idCorbeille;
    }

    // Getters et Setters
    public int getIdentifiantDechet() {
        return identifiantDechet;
    }

    public void setIdentifiantDechet(int identifiantDechet) {
        this.identifiantDechet = identifiantDechet;
    }

    public String getTypeDechet() {
        return typeDechet;
    }

    public void setTypeDechet(String typeDechet) {
        this.typeDechet = typeDechet;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public int getIdCorbeille() {
        return idCorbeille;
    }

    public void setIdCorbeille(int idCorbeille) {
        this.idCorbeille = idCorbeille;
    }
}
