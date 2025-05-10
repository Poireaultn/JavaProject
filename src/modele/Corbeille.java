package modele;

import java.util.ArrayList;
import java.util.List;

public class Corbeille {
    private int idCorbeille;
    private int idMenage;
    private List<Dechet> dechets = new ArrayList<>();

    public Corbeille(int idCorbeille, int idMenage) {
        this.idCorbeille = idCorbeille;
        this.idMenage = idMenage;
    }

    public int getIdCorbeille() {
        return idCorbeille;
    }

    public int getIdMenage() {
        return idMenage;
    }

    public void ajouterDechet(Dechet dechet) {
        dechets.add(dechet);
    }

    public List<Dechet> consulterDechets() {
        return dechets;
    }

    public int getIdentifiant() {
        return idCorbeille;
    }
}
