package modele;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceGestionPoubelle {

    public static List<Poubelle> getToutesLesPoubelles() {
        List<Poubelle> poubelles = new ArrayList<>();
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion()) {
            String requete = "SELECT * FROM poubelle";
            PreparedStatement statement = connexion.prepareStatement(requete);
            ResultSet resultat = statement.executeQuery();

            while (resultat.next()) {
                Poubelle p = new Poubelle(
                        resultat.getInt("idPoubelle"),
                        resultat.getString("ville"),
                        resultat.getString("quartier"),
                        resultat.getString("typePoubelle"),
                        resultat.getInt("capacite"),
                        resultat.getDouble("poids_actuel")
                );
                poubelles.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erreur (getToutesLesPoubelles) : " + e.getMessage());
        }

        return poubelles;
    }

    public static Poubelle recupererPoubelleParId(int idPoubelle) {
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion()) {
            String requete = "SELECT * FROM poubelle WHERE idPoubelle = ?";
            PreparedStatement statement = connexion.prepareStatement(requete);
            statement.setInt(1, idPoubelle);
            ResultSet resultat = statement.executeQuery();

            if (resultat.next()) {
                return new Poubelle(
                        resultat.getInt("idPoubelle"),
                        resultat.getString("ville"),
                        resultat.getString("quartier"),
                        resultat.getString("typePoubelle"),
                        resultat.getInt("capacite"),
                        resultat.getDouble("poids_actuel")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erreur (recupererPoubelleParId) : " + e.getMessage());
        }
        return null;
    }

    public static boolean ajouterPoubelle(String ville, String quartier, String typeDechet, int capacite) {
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion()) {
            String requete = "INSERT INTO poubelle (idMenage, ville, quartier, typePoubelle, capacite, poids_actuel) VALUES (NULL, ?, ?, ?, ?, 0)";
            PreparedStatement statement = connexion.prepareStatement(requete);
            statement.setString(1, ville);
            statement.setString(2, quartier);
            statement.setString(3, typeDechet);
            statement.setInt(4, capacite);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erreur (ajouterPoubelle) : " + e.getMessage());
            return false;
        }
    }

    public static boolean supprimerPoubelle(int idPoubelle) {
        Poubelle poubelle = recupererPoubelleParId(idPoubelle);
        if (poubelle == null || poubelle.getPoidsActuel() > 0) {
            return false;
        }

        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion()) {
            String requete = "DELETE FROM poubelle WHERE idPoubelle = ?";
            PreparedStatement statement = connexion.prepareStatement(requete);
            statement.setInt(1, idPoubelle);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erreur (supprimerPoubelle) : " + e.getMessage());
            return false;
        }
    }

    public static List<CentreDeTri> recupererCentresDeTri() {
        List<CentreDeTri> centres = new ArrayList<>();
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion()) {
            String requete = "SELECT * FROM centre_tri";
            PreparedStatement statement = connexion.prepareStatement(requete);
            ResultSet resultat = statement.executeQuery();

            while (resultat.next()) {
                centres.add(new CentreDeTri(
                        resultat.getInt("idCentre"),
                        resultat.getString("nom"),
                        resultat.getString("adresse")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erreur (recupererCentresDeTri) : " + e.getMessage());
        }

        return centres;
    }

    public static boolean viderPoubelleDansCentre(int idPoubelle, int idCentre, Menage menage) {
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion()) {
            Poubelle poubelle = recupererPoubelleParId(idPoubelle);
            if (poubelle == null) return false;

            double poidsVide = poubelle.getPoidsActuel();

            String requete = "UPDATE poubelle SET poids_actuel = 0 WHERE idPoubelle = ?";
            PreparedStatement statement = connexion.prepareStatement(requete);
            statement.setInt(1, idPoubelle);
            int lignes = statement.executeUpdate();

            if (lignes > 0) {
                enregistrerHistoriqueVidage(idPoubelle, idCentre, poidsVide, menage.getIdentifiant());
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erreur (viderPoubelleDansCentre) : " + e.getMessage());
        }
        return false;
    }

    public static void enregistrerHistoriqueVidage(int idPoubelle, int idCentre, double poidsVide, int idEmploye) {
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion()) {
            String requete = "INSERT INTO historique_vidages (idPoubelle, idCentre, idEmploye, poids_vidange, date_vidange) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
            PreparedStatement statement = connexion.prepareStatement(requete);
            statement.setInt(1, idPoubelle);
            statement.setInt(2, idCentre);
            statement.setInt(3, idEmploye);
            statement.setDouble(4, poidsVide);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur (enregistrerHistoriqueVidage) : " + e.getMessage());
        }
    }

    public static List<String> consulterHistoriqueVidages() {
        List<String> historique = new ArrayList<>();
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion()) {
            String requete = "SELECT * FROM historique_vidages";
            PreparedStatement statement = connexion.prepareStatement(requete);
            ResultSet resultat = statement.executeQuery();

            while (resultat.next()) {
                historique.add("- ID Historique : " + resultat.getInt("id") +
                        ", ID Poubelle : " + resultat.getInt("idPoubelle") +
                        ", ID Centre : " + resultat.getInt("idCentre") +
                        ", Poids vidé : " + resultat.getDouble("poids_vidange") + " kg" +
                        ", ID Employé : " + resultat.getInt("idEmploye") +
                        ", Date : " + resultat.getTimestamp("date_vidange"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur (consulterHistoriqueVidages) : " + e.getMessage());
        }

        return historique;
    }

    public static List<String> consulterHistoriqueDepots() {
        List<String> historique = new ArrayList<>();
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
             Statement statement = connexion.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, idPoubelle, idUtilisateur, poids_depose, date_depot FROM historique_depots")) {

            while (resultSet.next()) {
                historique.add("- ID Historique : " + resultSet.getInt("id") +
                        ", ID Poubelle : " + resultSet.getInt("idPoubelle") +
                        ", ID Utilisateur : " + resultSet.getInt("idUtilisateur") +
                        ", Poids déposé : " + resultSet.getDouble("poids_depose") + " kg" +
                        ", Date : " + resultSet.getTimestamp("date_depot"));
            }

        } catch (SQLException e) {
            System.out.println("Erreur (consulterHistoriqueDepots) : " + e.getMessage());
        }

        return historique;
    }
    
    public static List<Poubelle> getPoubellesBientotPleines() {
        List<Poubelle> poubellesPleines = new ArrayList<>();
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion()) {
            String requete = "SELECT * FROM poubelle WHERE poids_actuel >= capacite * 0.8";
            PreparedStatement statement = connexion.prepareStatement(requete);
            ResultSet resultat = statement.executeQuery();

            while (resultat.next()) {
                Poubelle p = new Poubelle(
                        resultat.getInt("idPoubelle"),
                        resultat.getString("ville"),
                        resultat.getString("quartier"),
                        resultat.getString("typePoubelle"),
                        resultat.getInt("capacite"),
                        resultat.getDouble("poids_actuel")
                );
                poubellesPleines.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erreur (getPoubellesBientotPleines) : " + e.getMessage());
        }

        return poubellesPleines;
    }
}
