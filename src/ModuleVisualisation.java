public class ModuleVisualisation {

    public static void afficherModule(Menage menage) {
        int choix;
        do {
            System.out.println("\n--- Module Visualisation ---");
            System.out.println("Bienvenue " + menage.getIdentifiantConnexion() + " !");
            System.out.println("1. Consulter mes points de fidélité");
            System.out.println("2. Accéder à la gestion de mes corbeilles");
            System.out.println("3. Accéder à la gestion de mes poubelles");
            System.out.println("4. Accéder à la gestion des vidages");
            System.out.println("0. Déconnexion");

            choix = OutilSaisie.lireEntier("Choisissez une option : ");

            switch (choix) {
                case 1:
                    afficherPointsFidelite(menage);
                    break;
                case 2:
                    ServiceGestionCorbeille.afficherGestionCorbeille(menage);
                    break;
                case 3:
                    ServiceGestionPoubelle.afficherGestionPoubelle(menage);
                    break;
                case 4:
                    ServiceGestionVidage.afficherGestionVidage(menage);
                    break;
                case 0:
                    System.out.println("Déconnexion...");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        } while (choix != 0);
    }

    private static void afficherPointsFidelite(Menage menage) {
        System.out.println("Vous avez actuellement " + menage.getPointsFidelite() + " points de fidélité.");
    }
}
