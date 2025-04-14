import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceGestionBons {

    public static void afficherMenuBons(Menage menage) {
        System.out.println("\n--- Achat de bons d'achat ---");
        System.out.println("Vos points de fidélité actuels : " + menage.getPointsFidelite() + " points");

        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "SELECT * FROM bons_achat";
                PreparedStatement statement = connexion.prepareStatement(requete);
                ResultSet resultat = statement.executeQuery();

                System.out.println("Bons d'achat disponibles :");
                while (resultat.next()) {
                    System.out.println("- ID : " + resultat.getInt("id") + 
                                       ", Marque : " + resultat.getString("marque") + 
                                       ", Valeur : " + resultat.getDouble("valeur") + "€" + 
                                       ", Coût en points : " + resultat.getDouble("cout_points"));
                }

                int idBon = OutilSaisie.lireEntier("Entrez l'ID du bon que vous souhaitez acheter : ");
                acheterBon(menage, idBon);

            } catch (SQLException e) {
                System.out.println("Erreur lors de l'affichage des bons d'achat : " + e.getMessage());
            }
        }
    }

    public static void acheterBon(Menage menage, int idBon) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "SELECT * FROM bons_achat WHERE id = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, idBon);
                ResultSet resultat = statement.executeQuery();

                if (resultat.next()) {
                    double coutPoints = resultat.getDouble("cout_points");
                    String marque = resultat.getString("marque");
                    double valeur = resultat.getDouble("valeur");

                    if (menage.getPointsFidelite() >= coutPoints) {
                        // Déduire les points de fidélité
                        double nouveauxPoints = menage.getPointsFidelite() - coutPoints;
                        String updatePoints = "UPDATE menage SET points_fidelite = ? WHERE id = ?";
                        PreparedStatement updateStatement = connexion.prepareStatement(updatePoints);
                        updateStatement.setDouble(1, nouveauxPoints);
                        updateStatement.setInt(2, menage.getIdentifiant());
                        updateStatement.executeUpdate();

                        menage.setPointsFidelite(nouveauxPoints);

                        System.out.println("Félicitations ! Vous avez acheté un bon d'achat de " + valeur + "€ pour la marque " + marque + ".");
                        System.out.println("Points de fidélité restants : " + nouveauxPoints);

                    } else {
                        System.out.println("Vous n'avez pas assez de points pour acheter ce bon.");
                    }
                } else {
                    System.out.println("Bon d'achat non trouvé.");
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de l'achat du bon : " + e.getMessage());
            }
        }
    }
}
