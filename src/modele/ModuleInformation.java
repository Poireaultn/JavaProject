package modele;

import java.util.ArrayList;
import java.util.List;

public class ModuleInformation {

    public static String getInformationsTri() {
        return "Le tri sélectif permet de mieux recycler les déchets en les séparant dès la collecte.\n"
             + "Cela facilite le traitement et la valorisation des matériaux recyclables.\n"
             + "Chaque geste compte pour préserver l'environnement !";
    }

    public static List<String> getTypesDeDechets() {
        List<String> types = new ArrayList<>();
        types.add("Déchets recyclables : papier, carton, plastiques, métal.");
        types.add("Déchets organiques : restes alimentaires, déchets verts.");
        types.add("Déchets dangereux : piles, peintures, produits chimiques.");
        types.add("Déchets non recyclables : certains plastiques souillés, etc.");
        return types;
    }
}
