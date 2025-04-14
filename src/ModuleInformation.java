public class ModuleInformation {

    public static void afficherModule() {
        int choix;
        do {
            System.out.println("\n--- Découverte libre - Module Information ---");
            System.out.println("1. En savoir plus sur le tri sélectif");
            System.out.println("2. Consulter les types de déchets");
            System.out.println("0. Retour au menu principal");

            choix = OutilSaisie.lireEntier("Choisissez une option : ");

            switch (choix) {
                case 1:
                    afficherInformationsTri();
                    break;
                case 2:
                    afficherTypesDeDechets();
                    break;
                case 0:
                    System.out.println("Retour au menu principal...");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        } while (choix != 0);
    }

    private static void afficherInformationsTri() {
        System.out.println("\nLe tri sélectif permet de mieux recycler les déchets en les séparant dès la collecte.");
        System.out.println("Cela facilite le traitement et la valorisation des matériaux recyclables.");
        System.out.println("Chaque geste compte pour préserver l'environnement !");
    }

    private static void afficherTypesDeDechets() {
        System.out.println("\nLes types de déchets courants :");
        System.out.println("- Déchets recyclables : papier, carton, plastiques, métal.");
        System.out.println("- Déchets organiques : restes alimentaires, déchets verts.");
        System.out.println("- Déchets dangereux : piles, peintures, produits chimiques.");
        System.out.println("- Déchets non recyclables : certains plastiques souillés, etc.");
    }
}
