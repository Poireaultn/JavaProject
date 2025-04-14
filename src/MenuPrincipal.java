import java.util.Scanner;

public class MenuPrincipal {

    public static void afficherMenu() {
        Scanner scanner = new Scanner(System.in);
        int choix;
        boolean estConnecte = false;
        Menage menage = new Menage();

        do {
            System.out.println("\n--- Menu Principal ---");

            if (estConnecte) {
                System.out.println("Connecté en tant que : " + menage.getRole());
                System.out.println("4. Ajouter un déchet à la corbeille");
                System.out.println("5. Consulter les déchets de ma corbeille");
                System.out.println("6. Vider les déchets dans une poubelle");
                System.out.println("7. Gestion des corbeilles");
                System.out.println("8. Gestion des poubelles");
                System.out.println("9. Jeter directement un déchet dans une poubelle");
                System.out.println("11. Acheter un bon d'achat 🛍️");

                // ✅ Options réservées aux employés
                if (menage.getRole().equalsIgnoreCase("employé")) {
                    System.out.println("10. Consulter l'historique des dépôts");
                }

            } else {
                System.out.println("1. Découverte libre (Visiteur)");
                System.out.println("3. Se connecter"); // 🧹 Suppression de "S'inscrire"
            }

            System.out.println("0. Quitter");
            choix = OutilSaisie.lireEntier("Votre choix : ");

            switch (choix) {
                case 1:
                    ModuleInformation.afficherModule();
                    break;
                case 3:
                    estConnecte = ServiceUtilisateur.connexion(menage);
                    break;
                case 4:
                    if (estConnecte) ajouterDechetDansCorbeille(menage);
                    else System.out.println("Veuillez vous connecter d'abord.");
                    break;
                case 5:
                    if (estConnecte) afficherDechetsCorbeille(menage);
                    else System.out.println("Veuillez vous connecter d'abord.");
                    break;
                case 6:
                    if (estConnecte) viderDechetsDansPoubelle(menage);
                    else System.out.println("Veuillez vous connecter d'abord.");
                    break;
                case 7:
                    if (estConnecte) afficherGestionCorbeilles(menage);
                    else System.out.println("Veuillez vous connecter d'abord.");
                    break;
                case 8:
                    if (estConnecte) ServiceGestionPoubelle.afficherGestionPoubelle(menage);
                    else System.out.println("Veuillez vous connecter d'abord.");
                    break;
                case 9:
                    if (estConnecte) jeterDechetDirectementDansPoubelle(menage);
                    else System.out.println("Veuillez vous connecter d'abord.");
                    break;
                case 10:
                    if (estConnecte && menage.getRole().equalsIgnoreCase("employé")) {
                        ServiceGestionDechet.consulterHistoriqueDepots();
                    } else {
                        System.out.println("Accès refusé. Option réservée aux employés.");
                    }
                    break;
                case 11:
                    if (estConnecte) ServiceGestionBons.afficherMenuBons(menage);
                    else System.out.println("Veuillez vous connecter d'abord.");
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }

        } while (choix != 0);
    }

    public static void afficherGestionCorbeilles(Menage menage) {
        int choix;
        do {
            System.out.println("\n--- Gestion des Corbeilles ---");
            System.out.println("1. Consulter mes corbeilles");
            System.out.println("2. Ajouter une nouvelle corbeille");
            System.out.println("3. Supprimer une corbeille");
            System.out.println("0. Retour au menu précédent");

            choix = OutilSaisie.lireEntier("Votre choix : ");

            switch (choix) {
                case 1:
                    ServiceGestionCorbeille.consulterCorbeilles(menage);
                    break;
                case 2:
                    ServiceGestionCorbeille.ajouterCorbeille(menage);
                    break;
                case 3:
                    ServiceGestionCorbeille.supprimerCorbeille(menage);
                    break;
                case 0:
                    System.out.println("Retour au menu principal...");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        } while (choix != 0);
    }

    public static void ajouterDechetDansCorbeille(Menage menage) {
        ServiceGestionCorbeille.consulterCorbeilles(menage);
        int idCorbeille = OutilSaisie.lireEntier("ID de la corbeille : ");
        Corbeille corbeille = new Corbeille(idCorbeille, menage.getIdentifiant());

        ServiceGestionDechet.afficherTypesDechets();
        int idTypeDechet = OutilSaisie.lireEntier("ID du type de déchet : ");
        String typeDechet = ServiceGestionDechet.recupererTypeDechetParId(idTypeDechet);

        if (typeDechet == null) {
            System.out.println("Type de déchet invalide.");
            return;
        }

        double poids = OutilSaisie.lireEntier("Poids du déchet : ");
        ServiceGestionDechet.ajouterDechetDansCorbeille(corbeille, typeDechet, poids);
    }

    public static void afficherDechetsCorbeille(Menage menage) {
        ServiceGestionCorbeille.consulterCorbeilles(menage);
        int idCorbeille = OutilSaisie.lireEntier("ID de la corbeille à consulter : ");
        Corbeille corbeille = new Corbeille(idCorbeille, menage.getIdentifiant());
        ServiceGestionDechet.consulterDechetsCorbeille(corbeille);
    }

    public static void viderDechetsDansPoubelle(Menage menage) {
        ServiceGestionCorbeille.consulterCorbeilles(menage);
        int idCorbeille = OutilSaisie.lireEntier("ID de la corbeille à vider : ");
        Corbeille corbeille = new Corbeille(idCorbeille, menage.getIdentifiant());

        ServiceGestionPoubelle.consulterPoubellesDisponibles();
        int idPoubelle = OutilSaisie.lireEntier("ID de la poubelle : ");
        Poubelle poubelle = ServiceGestionPoubelle.recupererPoubelleParId(idPoubelle);

        if (poubelle == null) {
            System.out.println("Poubelle invalide.");
            return;
        }

        var dechets = ServiceGestionDechet.recupererDechetsParCorbeille(idCorbeille);

        if (dechets.isEmpty()) {
            System.out.println("Aucun déchet à traiter dans cette corbeille.");
            return;
        }

        System.out.println("\nDéchets disponibles dans la corbeille :");
        for (Dechet dechet : dechets) {
            System.out.println("- ID : " + dechet.getIdentifiantDechet() + ", Type : " + dechet.getTypeDechet() + ", Poids : " + dechet.getPoids() + " kg");
        }

        int idDechet = OutilSaisie.lireEntier("Entrez l'ID du déchet à déposer : ");
        Dechet dechetChoisi = null;

        for (Dechet dechet : dechets) {
            if (dechet.getIdentifiantDechet() == idDechet) {
                dechetChoisi = dechet;
                break;
            }
        }

        if (dechetChoisi == null) {
            System.out.println("Déchet invalide.");
            return;
        }

        double quantiteSouhaitee = OutilSaisie.lireEntier("Quantité à déposer (kg) : ");
        if (quantiteSouhaitee <= 0) {
            System.out.println("Quantité invalide.");
            return;
        }

        ServiceGestionDechet.deposerDechetDansPoubelle(dechetChoisi, poubelle, menage, quantiteSouhaitee);
    }

    public static void jeterDechetDirectementDansPoubelle(Menage menage) {
        ServiceGestionDechet.afficherTypesDechets();
        int idTypeDechet = OutilSaisie.lireEntier("ID du type de déchet : ");
        String typeDechet = ServiceGestionDechet.recupererTypeDechetParId(idTypeDechet);

        if (typeDechet == null) {
            System.out.println("Type de déchet invalide.");
            return;
        }

        double poids = OutilSaisie.lireEntier("Poids du déchet : ");
        if (poids <= 0) {
            System.out.println("Poids invalide.");
            return;
        }

        ServiceGestionPoubelle.consulterPoubellesDisponibles();
        int idPoubelle = OutilSaisie.lireEntier("ID de la poubelle : ");
        Poubelle poubelle = ServiceGestionPoubelle.recupererPoubelleParId(idPoubelle);

        if (poubelle == null) {
            System.out.println("Poubelle invalide.");
            return;
        }

        Dechet dechetTemporaire = new Dechet(typeDechet, poids, -1);
        ServiceGestionDechet.deposerDechetDansPoubelle(dechetTemporaire, poubelle, menage, poids);
    }
}
