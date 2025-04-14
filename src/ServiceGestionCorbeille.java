import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceGestionCorbeille {

    public static void afficherGestionCorbeille(Menage menage) {
        int choix;
        do {
            System.out.println("\n--- Gestion des corbeilles ---");
            System.out.println("1. Consulter mes corbeilles");
            System.out.println("2. Ajouter une nouvelle corbeille");
            System.out.println("3. Supprimer une corbeille");
            System.out.println("0. Retour au menu précédent");

            choix = OutilSaisie.lireEntier("Choisissez une option : ");

            switch (choix) {
                case 1:
                    consulterCorbeilles(menage);
                    break;
                case 2:
                    ajouterCorbeille(menage);
                    break;
                case 3:
                    supprimerCorbeille(menage);
                    break;
                case 0:
                    System.out.println("Retour au menu précédent...");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        } while (choix != 0);
    }

    public static void consulterCorbeilles(Menage menage) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "SELECT idCorbeille FROM corbeille WHERE idMenage = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, menage.getIdentifiant());
                ResultSet resultat = statement.executeQuery();

                System.out.println("\nVos corbeilles :");
                while (resultat.next()) {
                    System.out.println("- Corbeille ID : " + resultat.getInt("idCorbeille"));
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de la consultation des corbeilles : " + e.getMessage());
            }
        }
    }


    public static void ajouterCorbeille(Menage menage) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "INSERT INTO corbeille (idMenage) VALUES (?)";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, menage.getIdentifiant());
                int lignes = statement.executeUpdate();

                if (lignes > 0) {
                    System.out.println("Corbeille ajoutée avec succès !");
                } else {
                    System.out.println("Échec de l'ajout de la corbeille.");
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout de la corbeille : " + e.getMessage());
            }
        }
    }

    public static void supprimerCorbeille(Menage menage) {
        int idCorbeille = OutilSaisie.lireEntier("Entrez l'ID de la corbeille à supprimer : ");
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "DELETE FROM corbeille WHERE idCorbeille = ? AND idMenage = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, idCorbeille);
                statement.setInt(2, menage.getIdentifiant());
                int lignes = statement.executeUpdate();

                if (lignes > 0) {
                    System.out.println("Corbeille supprimée avec succès !");
                } else {
                    System.out.println("Aucune corbeille trouvée avec cet identifiant.");
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de la suppression de la corbeille : " + "Corbeille non vide");
            }
        }
    }
}

	
