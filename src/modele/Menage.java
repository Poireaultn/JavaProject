package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Menage {
    private int identifiantMenage;
    private String identifiantConnexion;
    private String motDePasse;
    private double pointsFidelite;
    private String role; // utilisateur ou employé

    // Getters et setters

    public int getIdentifiant() {
        return identifiantMenage;
    }

    public void setIdentifiantMenage(int identifiantMenage) {
        this.identifiantMenage = identifiantMenage;
    }

    public String getIdentifiantConnexion() {
        return identifiantConnexion;
    }

    public void setIdentifiantConnexion(String identifiantConnexion) {
        this.identifiantConnexion = identifiantConnexion;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public double getPointsFidelite() {
        return pointsFidelite;
    }

    public void setPointsFidelite(double pointsFidelite) {
        this.pointsFidelite = pointsFidelite;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void ajouterPoints(double points) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        try {
            if (connexion != null) {
                String requete = "UPDATE menage SET points_fidelite = points_fidelite + ? WHERE id = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setDouble(1, points);
                statement.setInt(2, this.identifiantMenage);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement des points de fidélité : " + e.getMessage());
        }
        this.pointsFidelite += points;
    }

    public void retirerPoints(double points) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        try {
            if (connexion != null) {
                String requete = "UPDATE menage SET points_fidelite = points_fidelite - ? WHERE id = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setDouble(1, points);
                statement.setInt(2, this.identifiantMenage);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du retrait des points de fidélité : " + e.getMessage());
        }
        this.pointsFidelite -= points;
    }
}
