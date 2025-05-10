package modele;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceGestionBons {

    public static List<BonAchat> getBonsDisponibles() {
        List<BonAchat> bons = new ArrayList<>();
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();

        if (connexion != null) {
            try {
                String requete = "SELECT * FROM bons_achat";
                PreparedStatement statement = connexion.prepareStatement(requete);
                ResultSet resultat = statement.executeQuery();

                while (resultat.next()) {
                    int id = resultat.getInt("id");
                    double prix = resultat.getDouble("valeur");
                    String categorie = resultat.getString("marque");
                    int coutPoints = resultat.getInt("cout_points");

                    LocalDate dateExpiration;
                    try {
                        Date dateSQL = resultat.getDate("date_expiration");
                        dateExpiration = (dateSQL != null)
                                ? dateSQL.toLocalDate()
                                : LocalDate.now().plusYears(1);
                    } catch (SQLException e) {
                        System.out.println("⚠️ Colonne 'date_expiration' absente.");
                        dateExpiration = LocalDate.now().plusYears(1);
                    }

                    if (dateExpiration.isBefore(LocalDate.now())) continue;

                    BonAchat bon = new BonAchat(id, prix, categorie, dateExpiration, coutPoints);
                    bons.add(bon);
                }

            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la récupération des bons : " + e.getMessage());
            }
        }

        return bons;
    }

    public static boolean acheterBon(Menage menage, int idBon) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();

        if (connexion != null) {
            try {
                String requete = "SELECT * FROM bons_achat WHERE id = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, idBon);
                ResultSet resultat = statement.executeQuery();

                if (resultat.next()) {
                    int coutPoints = resultat.getInt("cout_points");

                    if (menage.getPointsFidelite() >= coutPoints) {
                        double nouveauxPoints = menage.getPointsFidelite() - coutPoints;

                        PreparedStatement update = connexion.prepareStatement("UPDATE menage SET points_fidelite = ? WHERE id = ?");
                        update.setDouble(1, nouveauxPoints);
                        update.setInt(2, menage.getIdentifiant());
                        update.executeUpdate();

                        PreparedStatement insert = connexion.prepareStatement(
                                "INSERT INTO achats_bons (id_menage, id_bon, date_achat) VALUES (?, ?, CURRENT_TIMESTAMP)"
                        );
                        insert.setInt(1, menage.getIdentifiant());
                        insert.setInt(2, idBon);
                        insert.executeUpdate();

                        menage.setPointsFidelite(nouveauxPoints);
                        return true;
                    }
                }

            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de l'achat du bon : " + e.getMessage());
            }
        }

        return false;
    }

    public static List<String> getBonsAchetesParMenage(Menage menage) {
        List<String> achetes = new ArrayList<>();
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();

        if (connexion != null) {
            try {
                String requete = "SELECT ba.id, ba.marque, ba.valeur, ba.date_expiration, ab.date_achat FROM bons_achat ba " +
                                 "JOIN achats_bons ab ON ba.id = ab.id_bon " +
                                 "WHERE ab.id_menage = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, menage.getIdentifiant());
                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    String info = "Bon ID " + rs.getInt("id")
                            + " | Marque : " + rs.getString("marque")
                            + " | Valeur : " + rs.getDouble("valeur") + "€"
                            + " | Expire le : " + rs.getDate("date_expiration")
                            + " | Acheté le : " + rs.getTimestamp("date_achat");
                    achetes.add(info);
                }

            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la récupération des bons achetés : " + e.getMessage());
            }
        }

        return achetes;
    }
    
}
