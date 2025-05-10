package modele;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceGestionDechet {

    public static List<String> recupererTousLesTypesDeDechets() {
        List<String> types = new ArrayList<>();
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "SELECT type_dechet FROM types_dechets";
                PreparedStatement statement = connexion.prepareStatement(requete);
                ResultSet resultat = statement.executeQuery();
                while (resultat.next()) {
                    types.add(resultat.getString("type_dechet"));
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération des types de déchets : " + e.getMessage());
            }
        }
        return types;
    }

    public static String recupererTypeDechetParId(int id) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "SELECT type_dechet FROM types_dechets WHERE id = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, id);
                ResultSet resultat = statement.executeQuery();
                if (resultat.next()) {
                    return resultat.getString("type_dechet");
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération du type de déchet : " + e.getMessage());
            }
        }
        return null;
    }

    public static boolean ajouterDechetDansCorbeille(Corbeille corbeille, String typeDechet, double poids) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "INSERT INTO dechet (type_dechet, poids, id_corbeille) VALUES (?, ?, ?)";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setString(1, typeDechet);
                statement.setDouble(2, poids);
                statement.setInt(3, corbeille.getIdCorbeille());
                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout du déchet : " + e.getMessage());
            }
        }
        return false;
    }

    public static List<Dechet> recupererDechetsParCorbeille(int idCorbeille) {
        List<Dechet> dechets = new ArrayList<>();
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "SELECT * FROM dechet WHERE id_corbeille = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, idCorbeille);
                ResultSet resultat = statement.executeQuery();

                while (resultat.next()) {
                    Dechet dechet = new Dechet(
                        resultat.getInt("id"),
                        resultat.getString("type_dechet"),
                        resultat.getDouble("poids"),
                        idCorbeille
                    );
                    dechets.add(dechet);
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération des déchets : " + e.getMessage());
            }
        }
        return dechets;
    }

    public static boolean supprimerDechet(Dechet dechet) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        try {
            if (connexion != null) {
                String requete = "DELETE FROM dechet WHERE id = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, dechet.getIdentifiantDechet());
                return statement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du déchet : " + e.getMessage());
        }
        return false;
    }

    public static boolean verifierTypeDeDechet(String typeDechet, String typePoubelle) {
        return typeDechet.equalsIgnoreCase(typePoubelle);
    }

    public static boolean mettreAJourPoubelle(Poubelle poubelle, double quantiteDejetee) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                double nouveauPoids = poubelle.getPoidsActuel() + quantiteDejetee;
                String requete = "UPDATE poubelle SET poids_actuel = ? WHERE idPoubelle = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setDouble(1, nouveauPoids);
                statement.setInt(2, poubelle.getIdentifiantPoubelle());
                int lignes = statement.executeUpdate();
                if (lignes > 0) {
                    poubelle.setPoidsActuel(nouveauPoids);
                    return true;
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la mise à jour de la poubelle : " + e.getMessage());
            }
        }
        return false;
    }

    public static void mettreAJourDechet(Dechet dechet) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        try {
            if (connexion != null) {
                String requete = "UPDATE dechet SET poids = ? WHERE id = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setDouble(1, dechet.getPoids());
                statement.setInt(2, dechet.getIdentifiantDechet());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du déchet : " + e.getMessage());
        }
    }

    public static void enregistrerHistoriqueDepot(int idPoubelle, int idUtilisateur, double poidsDepose) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        try {
            if (connexion != null) {
                String requete = "INSERT INTO historique_depots (idPoubelle, idUtilisateur, poids_depose, date_depot) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, idPoubelle);
                statement.setInt(2, idUtilisateur);
                statement.setDouble(3, poidsDepose);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement de l'historique du dépôt : " + e.getMessage());
        }
    }

    public static boolean deposerDechetDansPoubelle(Dechet dechet, Poubelle poubelle, Menage menage, double quantiteSouhaitee) {
        System.out.println("=== Tentative de dépôt ===");
        System.out.println("→ Type déchet: " + dechet.getTypeDechet() + " | Type poubelle: " + poubelle.getTypePoubelle());
        System.out.println("→ Quantité demandée: " + quantiteSouhaitee + "kg | Poids dans déchet: " + dechet.getPoids() + "kg");
        System.out.println("→ Capacité poubelle: " + poubelle.getCapacite() + "kg | Actuel: " + poubelle.getPoidsActuel() + "kg");

        boolean typeCorrect = verifierTypeDeDechet(dechet.getTypeDechet(), poubelle.getTypePoubelle());
        double capaciteRestante = poubelle.getCapacite() - poubelle.getPoidsActuel();
        double poidsDechetDisponible = dechet.getPoids();

        if (quantiteSouhaitee <= 0) {
            System.out.println("❌ Quantité invalide.");
            return false;
        }

        if (capaciteRestante <= 0) {
            System.out.println("❌ Poubelle pleine.");
            return false;
        }

        double poidsAJeter = Math.min(quantiteSouhaitee, Math.min(poidsDechetDisponible, capaciteRestante));

        if (poidsAJeter <= 0) {
            System.out.println("❌ Rien à déposer.");
            return false;
        }

        dechet.setPoids(dechet.getPoids() - poidsAJeter);
        mettreAJourPoubelle(poubelle, poidsAJeter);

        if (dechet.getPoids() <= 0) {
            supprimerDechet(dechet);
        } else {
            mettreAJourDechet(dechet);
        }

        enregistrerHistoriqueDepot(poubelle.getIdentifiantPoubelle(), menage.getIdentifiant(), poidsAJeter);

        menage.ajouterPoints(Math.round(poidsAJeter));

        System.out.println("✅ Dépôt réussi de " + poidsAJeter + "kg");

        return true;
    }

    public static List<String> consulterHistoriqueDepots() {
        List<String> historique = new ArrayList<>();
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "SELECT * FROM historique_depots";
                PreparedStatement statement = connexion.prepareStatement(requete);
                ResultSet resultat = statement.executeQuery();

                while (resultat.next()) {
                    String ligne = "ID Dépôt : " + resultat.getInt("id")
                                 + ", Poubelle : " + resultat.getInt("idPoubelle")
                                 + ", Utilisateur : " + resultat.getInt("idUtilisateur")
                                 + ", Poids : " + resultat.getDouble("poids_depose") + "kg"
                                 + ", Date : " + resultat.getTimestamp("date_depot");
                    historique.add(ligne);
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de la consultation de l'historique des dépôts : " + e.getMessage());
            }
        }
        return historique;
    }
}
