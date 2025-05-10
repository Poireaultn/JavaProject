package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceGestionVidage {

    public static List<String> consulterVidages(Menage menage) {
        List<String> vidages = new ArrayList<>();

        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion()) {
            String requete = "SELECT * FROM vidage WHERE idMenage = ?";
            PreparedStatement statement = connexion.prepareStatement(requete);
            statement.setInt(1, menage.getIdentifiant());
            ResultSet resultat = statement.executeQuery();

            while (resultat.next()) {
                String ligne = "- Vidage ID : " + resultat.getInt("idVidage")
                        + ", Quantité : " + resultat.getInt("quantite")
                        + ", Date : " + resultat.getTimestamp("heureDepot")
                        + ", Type de déchet : " + resultat.getString("typeDechet");
                vidages.add(ligne);
            }

        } catch (SQLException e) {
            System.out.println("Erreur (consulterVidages) : " + e.getMessage());
        }

        return vidages;
    }

    public static boolean ajouterVidage(Menage menage, int quantite, String typeDechet) {
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion()) {
            String requete = "INSERT INTO vidage (idMenage, quantite, heureDepot, typeDechet) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connexion.prepareStatement(requete);
            statement.setInt(1, menage.getIdentifiant());
            statement.setInt(2, quantite);
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(4, typeDechet);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur (ajouterVidage) : " + e.getMessage());
            return false;
        }
    }
}
