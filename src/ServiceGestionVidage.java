import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ServiceGestionVidage {

    public static void afficherGestionVidage(Menage menage) {
        int choix;
        do {
            System.out.println("\n--- Gestion des vidages ---");
            System.out.println("1. Consulter mes vidages");
            System.out.println("2. Ajouter un nouveau vidage");
            System.out.println("0. Retour au menu précédent");

            choix = OutilSaisie.lireEntier("Choisissez une option : ");

            switch (choix) {
                case 1:
                    consulterVidages(menage);
                    break;
                case 2:
                    ajouterVidage(menage);
                    break;
                case 0:
                    System.out.println("Retour au menu précédent...");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        } while (choix != 0);
    }

    private static void consulterVidages(Menage menage) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "SELECT * FROM vidage WHERE idMenage = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, menage.getIdentifiant());
                ResultSet resultat = statement.executeQuery();

                System.out.println("Vos vidages :");
                while (resultat.next()) {
                    System.out.println("- Vidage ID : " + resultat.getInt("idVidage")
                            + ", Quantité : " + resultat.getInt("quantite")
                            + ", Date : " + resultat.getTimestamp("heureDepot")
                            + ", Type de déchet : " + resultat.getString("typeDechet"));
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de la consultation des vidages : " + e.getMessage());
            }
        }
    }

    private static void ajouterVidage(Menage menage) {
        int quantite = OutilSaisie.lireEntier("Entrez la quantité de déchets : ");
        String typeDechet = OutilSaisie.lireTexte("Entrez le type de déchet : ");

        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "INSERT INTO vidage (idMenage, quantite, heureDepot, typeDechet) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, menage.getIdentifiant());
                statement.setInt(2, quantite);
                statement.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDateTime.now()));
                statement.setString(4, typeDechet);

                int lignes = statement.executeUpdate();

                if (lignes > 0) {
                    System.out.println("Vidage ajouté avec succès !");
                } else {
                    System.out.println("Échec de l'ajout du vidage.");
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout du vidage : " + e.getMessage());
            }
        }
    }
}
