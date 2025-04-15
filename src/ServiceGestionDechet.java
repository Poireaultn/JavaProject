import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceGestionDechet {

    public static void afficherTypesDechets() {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "SELECT id, type_dechet FROM types_dechets";
                PreparedStatement statement = connexion.prepareStatement(requete);
                ResultSet resultat = statement.executeQuery();

                System.out.println("\nListe des types de déchets disponibles :");
                while (resultat.next()) {
                    int id = resultat.getInt("id");
                    String type = resultat.getString("type_dechet");
                    System.out.println("- ID : " + id + ", Type : " + type);
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération des types de déchets : " + e.getMessage());
            }
        }
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

    public static void ajouterDechetDansCorbeille(Corbeille corbeille, String typeDechet, double poids) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "INSERT INTO dechet (type_dechet, poids, id_corbeille) VALUES (?, ?, ?)";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setString(1, typeDechet);
                statement.setDouble(2, poids);
                statement.setInt(3, corbeille.getIdCorbeille());
                int lignes = statement.executeUpdate();

                if (lignes > 0) {
                    System.out.println("Déchet ajouté à la corbeille.");
                } else {
                    System.out.println("Échec de l'ajout du déchet.");
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout du déchet : " + e.getMessage());
            }
        }
    }

    public static void consulterDechetsCorbeille(Corbeille corbeille) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "SELECT id, type_dechet, poids FROM dechet WHERE id_corbeille = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, corbeille.getIdCorbeille());
                ResultSet resultat = statement.executeQuery();

                System.out.println("\nDéchets dans la corbeille ID : " + corbeille.getIdCorbeille());
                boolean aDesDechets = false;

                while (resultat.next()) {
                    aDesDechets = true;
                    int idDechet = resultat.getInt("id");
                    String typeDechet = resultat.getString("type_dechet");
                    double poids = resultat.getDouble("poids");

                    System.out.println("- ID Déchet : " + idDechet + ", Type : " + typeDechet + ", Poids : " + poids + " kg");
                }

                if (!aDesDechets) {
                    System.out.println("Aucun déchet dans la corbeille.");
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de la consultation des déchets de la corbeille : " + e.getMessage());
            }
        }
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
                        resultat.getString("type_dechet"),
                        resultat.getDouble("poids"),
                        idCorbeille
                    );
                    dechet.setIdentifiantDechet(resultat.getInt("id"));
                    dechets.add(dechet);
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération des déchets : " + e.getMessage());
            }
        }
        return dechets;
    }

    public static void supprimerDechet(Dechet dechet) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "DELETE FROM dechet WHERE id = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, dechet.getIdentifiantDechet());
                statement.executeUpdate();

                System.out.println("Le déchet a été supprimé de la corbeille.");

            } catch (SQLException e) {
                System.out.println("Erreur lors de la suppression du déchet : " + e.getMessage());
            }
        }
    }

    public static boolean verifierTypeDeDechet(String typeDechet, String typePoubelle) {
        return typeDechet.equalsIgnoreCase(typePoubelle);
    }

    public static void mettreAJourPoubelle(Poubelle poubelle, double quantiteDejetee) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                double nouveauPoids = poubelle.getPoidsActuel() + quantiteDejetee;
                String requete = "UPDATE poubelle SET poids_actuel = ? WHERE idPoubelle = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setDouble(1, nouveauPoids);
                statement.setInt(2, poubelle.getIdentifiantPoubelle());
                statement.executeUpdate();

                poubelle.setPoidsActuel(nouveauPoids);

            } catch (SQLException e) {
                System.out.println("Erreur lors de la mise à jour de la poubelle : " + e.getMessage());
            }
        }
    }

    public static void mettreAJourDechet(Dechet dechet) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "UPDATE dechet SET poids = ? WHERE id = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setDouble(1, dechet.getPoids());
                statement.setInt(2, dechet.getIdentifiantDechet());
                statement.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Erreur lors de la mise à jour du déchet : " + e.getMessage());
            }
        }
    }

    public static void enregistrerHistoriqueDepot(int idPoubelle, int idUtilisateur, double poidsDepose) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "INSERT INTO historique_depots (idPoubelle, idUtilisateur, poids_depose, date_depot) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, idPoubelle);
                statement.setInt(2, idUtilisateur);
                statement.setDouble(3, poidsDepose);
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'enregistrement de l'historique du dépôt : " + e.getMessage());
            }
        }
    }


    public static void deposerDechetDansPoubelle(Dechet dechet, Poubelle poubelle, Menage menage, double quantiteSouhaitee) {
        boolean typeCorrect = verifierTypeDeDechet(dechet.getTypeDechet(), poubelle.getTypePoubelle());

        double capaciteRestante = poubelle.getCapacite() - poubelle.getPoidsActuel();
        double poidsDechet = Math.min(dechet.getPoids(), quantiteSouhaitee);

        if (capaciteRestante <= 0) {
            System.out.println("La poubelle est pleine. Impossible de jeter ce déchet pour le moment.");
            return;
        }

        double poidsAjoute = Math.min(poidsDechet, capaciteRestante);

        dechet.setPoids(dechet.getPoids() - poidsAjoute);

        mettreAJourPoubelle(poubelle, poidsAjoute);

        if (dechet.getPoids() <= 0) {
            supprimerDechet(dechet);
        } else {
            mettreAJourDechet(dechet);
        }

        enregistrerHistoriqueDepot(poubelle.getIdentifiantPoubelle(), menage.getIdentifiant(), poidsAjoute);

        if (typeCorrect) {
            double points = Math.round(poidsAjoute);
            menage.ajouterPoints(points);
            System.out.println("Bravo ! Vous gagnez " + points + " points de fidélité. Points totaux : " + menage.getPointsFidelite());
        } else {
            double malus = Math.round(poidsAjoute * 5);
            menage.retirerPoints(malus);
            System.out.println("Attention : déchet mal trié. Vous perdez " + malus + " points de fidélité. Points restants : " + menage.getPointsFidelite());
        }

        System.out.println("Quantité déposée : " + poidsAjoute + " kg. Capacité restante de la poubelle : " + (capaciteRestante - poidsAjoute) + " kg.");
    }

    public static void consulterHistoriqueDepots() {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "SELECT * FROM historique_depots";
                PreparedStatement statement = connexion.prepareStatement(requete);
                ResultSet resultat = statement.executeQuery();

                System.out.println("Historique des dépôts :");
                while (resultat.next()) {
                    System.out.println("- ID Historique : " + resultat.getInt("id") +
                                       ", ID Poubelle : " + resultat.getInt("idPoubelle") +
                                       ", ID Utilisateur : " + resultat.getInt("idUtilisateur") +
                                       ", Poids déposé : " + resultat.getDouble("poids_depose") + " kg" +
                                       ", Date : " + resultat.getTimestamp("date_depot"));
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de la consultation de l'historique des dépôts : " + e.getMessage());
            }
        }
    }
}
