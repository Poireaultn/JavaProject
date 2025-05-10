package modele;
public class Commerce {
    private int identifiantCommerce;
    private String nom;

    public Commerce(int identifiantCommerce, String nom) {
        this.identifiantCommerce = identifiantCommerce;
        this.nom = nom;
    }

    public int getIdentifiantCommerce() {
        return identifiantCommerce;
    }

    public String getNom() {
        return nom;
    }
}
