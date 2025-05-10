package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceUtilisateur {

    public static boolean inscription(String identifiant, String motDePasse, String role) {
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion()) {
            String requete = "INSERT INTO menage (login, mot_de_passe, points_fidelite, role) VALUES (?, ?, 0, ?)";
            PreparedStatement statement = connexion.prepareStatement(requete);
            statement.setString(1, identifiant);
            statement.setString(2, motDePasse);
            statement.setString(3, role);

            int lignesAffectees = statement.executeUpdate();
            return lignesAffectees > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'inscription : " + e.getMessage());
            return false;
        }
    }

    public static Menage connexion(String identifiant, String motDePasse) {
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion()) {
            String requete = "SELECT * FROM menage WHERE login = ? AND mot_de_passe = ?";
            PreparedStatement statement = connexion.prepareStatement(requete);
            statement.setString(1, identifiant);
            statement.setString(2, motDePasse);
            ResultSet resultat = statement.executeQuery();

            if (resultat.next()) {
                Menage menage = new Menage();
                menage.setIdentifiantMenage(resultat.getInt("id"));
                menage.setIdentifiantConnexion(resultat.getString("login"));
                menage.setMotDePasse(resultat.getString("mot_de_passe"));
                menage.setPointsFidelite(resultat.getInt("points_fidelite"));
                menage.setRole(resultat.getString("role"));

                return menage;
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
        }
        return null;
    }
}
