package modele;

import java.util.List;

public class MenuPrincipal {

    public static void ajouterDechetDansCorbeille(Menage menage, int idCorbeille, int idTypeDechet, double poids) {
        Corbeille corbeille = new Corbeille(idCorbeille, menage.getIdentifiant());
        String typeDechet = ServiceGestionDechet.recupererTypeDechetParId(idTypeDechet);
        if (typeDechet != null && poids > 0) {
            ServiceGestionDechet.ajouterDechetDansCorbeille(corbeille, typeDechet, poids);
        }
    }

    public static List<Dechet> recupererDechetsDeCorbeille(Menage menage, int idCorbeille) {
        return ServiceGestionDechet.recupererDechetsParCorbeille(idCorbeille);
    }

    public static void viderDechetDansPoubelle(Menage menage, int idCorbeille, int idDechet, int idPoubelle, double quantite) {
        Corbeille corbeille = new Corbeille(idCorbeille, menage.getIdentifiant());
        Poubelle poubelle = ServiceGestionPoubelle.recupererPoubelleParId(idPoubelle);
        List<Dechet> dechets = ServiceGestionDechet.recupererDechetsParCorbeille(idCorbeille);
        
        for (Dechet dechet : dechets) {
            if (dechet.getIdentifiantDechet() == idDechet) {
                ServiceGestionDechet.deposerDechetDansPoubelle(dechet, poubelle, menage, quantite);
                break;
            }
        }
    }

    public static void jeterDechetDirectementDansPoubelle(Menage menage, int idTypeDechet, double poids, int idPoubelle) {
        String typeDechet = ServiceGestionDechet.recupererTypeDechetParId(idTypeDechet);
        Poubelle poubelle = ServiceGestionPoubelle.recupererPoubelleParId(idPoubelle);

        if (typeDechet != null && poubelle != null && poids > 0) {
            Dechet dechetTemp = new Dechet(typeDechet, poids, -1); 
            ServiceGestionDechet.deposerDechetDansPoubelle(dechetTemp, poubelle, menage, poids);
        }
    }
}
