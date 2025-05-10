package modele;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceGestionCorbeille {

    public static List<Corbeille> consulterCorbeilles(Menage menage) {
        List<Corbeille> corbeilles = new ArrayList<>();
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();

        if (connexion != null) {
            try {
                String requete = "SELECT idCorbeille FROM corbeille WHERE idMenage = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, menage.getIdentifiant());
                ResultSet resultat = statement.executeQuery();

                while (resultat.next()) {
                    int idCorbeille = resultat.getInt("idCorbeille");
                    corbeilles.add(new Corbeille(idCorbeille, menage.getIdentifiant()));
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération des corbeilles : " + e.getMessage());
            }
        }

        return corbeilles;
    }

    public static boolean ajouterCorbeille(Menage menage) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();

        if (connexion != null) {
            try {
                String requete = "INSERT INTO corbeille (idMenage) VALUES (?)";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, menage.getIdentifiant());
                int lignes = statement.executeUpdate();
                return lignes > 0;

            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout de la corbeille : " + e.getMessage());
            }
        }

        return false;
    }

    public static boolean supprimerCorbeille(Menage menage, int idCorbeille) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();

        if (connexion != null) {
            try {
                String requete = "DELETE FROM corbeille WHERE idCorbeille = ? AND idMenage = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, idCorbeille);
                statement.setInt(2, menage.getIdentifiant());
                int lignes = statement.executeUpdate();
                return lignes > 0;

            } catch (SQLException e) {
                System.out.println("Erreur lors de la suppression de la corbeille : " + e.getMessage());
            }
        }

        return false;
    }

    public static boolean viderDansPoubelle(Menage menage, int idCorbeille, int idDechet, int idPoubelle, double quantite) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();

        if (connexion != null) {
            try {
                String checkCorbeille = "SELECT * FROM corbeille WHERE idCorbeille = ? AND idMenage = ?";
                PreparedStatement stmtCorbeille = connexion.prepareStatement(checkCorbeille);
                stmtCorbeille.setInt(1, idCorbeille);
                stmtCorbeille.setInt(2, menage.getIdentifiant());
                ResultSet rs = stmtCorbeille.executeQuery();
                if (!rs.next()) return false;

                String selectDechet = "SELECT type_dechet, poids FROM dechet WHERE id = ? AND id_corbeille = ?";
                PreparedStatement stmtDechet = connexion.prepareStatement(selectDechet);
                stmtDechet.setInt(1, idDechet);
                stmtDechet.setInt(2, idCorbeille);
                ResultSet rsDechet = stmtDechet.executeQuery();
                if (!rsDechet.next()) return false;

                String typeDechet = rsDechet.getString("type_dechet");
                double poidsActuel = rsDechet.getDouble("poids");

                if (quantite > poidsActuel) return false;

                String selectPoubelle = "SELECT typePoubelle, poids_actuel, capacite FROM poubelle WHERE idPoubelle = ?";
                PreparedStatement stmtPoubelle = connexion.prepareStatement(selectPoubelle);
                stmtPoubelle.setInt(1, idPoubelle);
                ResultSet rsPoubelle = stmtPoubelle.executeQuery();
                if (!rsPoubelle.next()) return false;

                String typePoubelle = rsPoubelle.getString("typePoubelle");
                double poidsPoubelle = rsPoubelle.getDouble("poids_actuel");
                double capacitePoubelle = rsPoubelle.getDouble("capacite");

                if (!typeDechet.equalsIgnoreCase(typePoubelle)) return false;
                if (poidsPoubelle + quantite > capacitePoubelle) return false;

                String updateDechet = "UPDATE dechet SET poids = poids - ? WHERE id = ?";
                PreparedStatement stmtUpdateDechet = connexion.prepareStatement(updateDechet);
                stmtUpdateDechet.setDouble(1, quantite);
                stmtUpdateDechet.setInt(2, idDechet);
                stmtUpdateDechet.executeUpdate();

                String updatePoubelle = "UPDATE poubelle SET poids_actuel = poids_actuel + ? WHERE idPoubelle = ?";
                PreparedStatement stmtUpdatePoubelle = connexion.prepareStatement(updatePoubelle);
                stmtUpdatePoubelle.setDouble(1, quantite);
                stmtUpdatePoubelle.setInt(2, idPoubelle);
                stmtUpdatePoubelle.executeUpdate();

                ServiceGestionDechet.enregistrerHistoriqueDepot(idPoubelle, menage.getIdentifiant(), quantite);

                int pointsGagnes = (int) quantite; 
                double nouveauxPoints = menage.getPointsFidelite() + pointsGagnes;

                String updatePoints = "UPDATE menage SET points_fidelite = ? WHERE id = ?";
                PreparedStatement stmtUpdatePoints = connexion.prepareStatement(updatePoints);
                stmtUpdatePoints.setDouble(1, nouveauxPoints);
                stmtUpdatePoints.setInt(2, menage.getIdentifiant());
                stmtUpdatePoints.executeUpdate();

                menage.setPointsFidelite(nouveauxPoints);

                if (poidsActuel - quantite <= 0) {
                    String deleteDechet = "DELETE FROM dechet WHERE id = ?";
                    PreparedStatement stmtDelete = connexion.prepareStatement(deleteDechet);
                    stmtDelete.setInt(1, idDechet);
                    stmtDelete.executeUpdate();
                }

                return true;

            } catch (SQLException e) {
                System.out.println("Erreur lors du vidage de la corbeille : " + e.getMessage());
            }
        }

        return false;
    }



}
