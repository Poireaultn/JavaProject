import java.sql.*;

public class ServiceUtilisateur {

    public static boolean connexion(Menage menage) {
        String identifiant = OutilSaisie.lireTexte("Entrez votre identifiant : ");
        String motDePasse = OutilSaisie.lireTexte("Entrez votre mot de passe : ");

        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion()) {
            String requete = "SELECT * FROM menage WHERE login = ? AND mot_de_passe = ?";
            PreparedStatement statement = connexion.prepareStatement(requete);
            statement.setString(1, identifiant);
            statement.setString(2, motDePasse);
            ResultSet resultat = statement.executeQuery();

            if (resultat.next()) {
                menage.setIdentifiantMenage(resultat.getInt("id"));
                menage.setIdentifiantConnexion(resultat.getString("login"));
                menage.setMotDePasse(resultat.getString("mot_de_passe"));
                menage.setPointsFidelite(resultat.getInt("points_fidelite"));
                menage.setRole(resultat.getString("role"));

                System.out.println("Connexion réussie ! Bienvenue, " + resultat.getString("login") + ".");
                System.out.println("Rôle : " + menage.getRole());

                return true;
            } else {
                System.out.println("Identifiants incorrects.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
            return false;
        }
    }

}
