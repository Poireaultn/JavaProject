import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class ServiceGestionPoubelle {

    public static void afficherGestionPoubelle(Menage menage) {
        int choix;
        do {
            System.out.println("\n--- Gestion des poubelles ---");
            System.out.println("1. Consulter les poubelles");
            if (menage.getRole().equalsIgnoreCase("employé")) {
                System.out.println("2. Ajouter une poubelle");
                System.out.println("3. Supprimer une poubelle");
                System.out.println("4. Vider une poubelle dans le centre de tri");
                System.out.println("5. Consulter l'historique des vidages");
                System.out.println("6. Consulter l'historique des dépôts");
            }
            System.out.println("0. Retour au menu précédent");

            choix = OutilSaisie.lireEntier("Votre choix : ");

            switch (choix) {
                case 1:
                    consulterPoubelles();
                    break;
                case 2:
                    if (menage.getRole().equalsIgnoreCase("employé")) {
                        ajouterPoubelle();
                    } else {
                        System.out.println("Accès refusé. Option réservée aux employés.");
                    }
                    break;
                case 3:
                    if (menage.getRole().equalsIgnoreCase("employé")) {
                        supprimerPoubelle();
                    } else {
                        System.out.println("Accès refusé. Option réservée aux employés.");
                    }
                    break;
                case 4:
                    if (menage.getRole().equalsIgnoreCase("employé")) {
                        viderPoubelleCentreDeTri(menage);
                    } else {
                        System.out.println("Accès refusé. Option réservée aux employés.");
                    }
                    break;
                case 5:
                    if (menage.getRole().equalsIgnoreCase("employé")) {
                        consulterHistoriqueVidages();
                    } else {
                        System.out.println("Accès refusé. Option réservée aux employés.");
                    }
                    break;
                case 6:
                    if (menage.getRole().equalsIgnoreCase("employé")) {
                        consulterHistoriqueDepots();
                    } else {
                        System.out.println("Accès refusé. Option réservée aux employés.");
                    }
                    break;
                case 0:
                    System.out.println("Retour au menu précédent...");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        } while (choix != 0);
    }

    public static void consulterPoubelles() {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "SELECT * FROM poubelle";
                PreparedStatement statement = connexion.prepareStatement(requete);
                ResultSet resultat = statement.executeQuery();

                System.out.println("Poubelles disponibles :");
                while (resultat.next()) {
                    System.out.println("- ID : " + resultat.getInt("idPoubelle") +
                                       ", Ville : " + resultat.getString("ville") +
                                       ", Quartier : " + resultat.getString("quartier") +
                                       ", Type : " + resultat.getString("typePoubelle") +
                                       ", Capacité restante : " + resultat.getInt("capacite") +
                                       " kg, Poids actuel : " + resultat.getInt("poids_actuel") + " kg");
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de la consultation des poubelles : " + e.getMessage());
            }
        }
    }

    public static void consulterPoubellesDisponibles() {
        consulterPoubelles();
    }

    public static Poubelle recupererPoubelleParId(int idPoubelle) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
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
                        resultat.getInt("poids_actuel")
                    );
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération de la poubelle : " + e.getMessage());
            }
        }
        return null;
    }

    public static void ajouterPoubelle() {
        System.out.println("Types de déchets disponibles pour la poubelle :");
        ServiceGestionDechet.afficherTypesDechets();

        String ville = OutilSaisie.lireTexte("Entrez la ville : ");
        String quartier = OutilSaisie.lireTexte("Entrez le quartier : ");
        int idTypeDechet = OutilSaisie.lireEntier("Entrez l'ID du type de déchet pour la poubelle : ");
        String typeDechet = ServiceGestionDechet.recupererTypeDechetParId(idTypeDechet);

        if (typeDechet == null) {
            System.out.println("Type de déchet invalide.");
            return;
        }

        int capacite = OutilSaisie.lireEntier("Entrez la capacité de la poubelle : ");

        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "INSERT INTO poubelle (idMenage, ville, quartier, typePoubelle, capacite, poids_actuel) VALUES (NULL, ?, ?, ?, ?, 0)";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setString(1, ville);
                statement.setString(2, quartier);
                statement.setString(3, typeDechet);
                statement.setInt(4, capacite);

                int lignes = statement.executeUpdate();

                if (lignes > 0) {
                    System.out.println("Poubelle ajoutée avec succès !");
                } else {
                    System.out.println("Échec de l'ajout de la poubelle.");
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout de la poubelle : " + e.getMessage());
            }
        }
    }

    public static void supprimerPoubelle() {
        consulterPoubelles();

        int idPoubelle = OutilSaisie.lireEntier("Entrez l'ID de la poubelle à supprimer : ");

        Poubelle poubelle = recupererPoubelleParId(idPoubelle);

        if (poubelle == null) {
            System.out.println("Poubelle non trouvée.");
            return;
        }

        if (poubelle.getPoidsActuel() > 0) {
            System.out.println("La poubelle n'est pas vide, impossible de la supprimer !");
            return;
        }

        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "DELETE FROM poubelle WHERE idPoubelle = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, idPoubelle);
                int lignes = statement.executeUpdate();

                if (lignes > 0) {
                    System.out.println("Poubelle supprimée avec succès !");
                } else {
                    System.out.println("Aucune poubelle trouvée avec cet identifiant.");
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de la suppression de la poubelle : " + e.getMessage());
            }
        }
    }

    public static void viderPoubelleCentreDeTri(Menage menage) {
        consulterPoubellesDisponibles();
        int idPoubelle = OutilSaisie.lireEntier("ID de la poubelle à vider : ");

        afficherCentresDeTri();
        int idCentre = OutilSaisie.lireEntier("ID du centre de tri : ");

        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                Poubelle poubelle = recupererPoubelleParId(idPoubelle);
                if (poubelle == null) {
                    System.out.println("Poubelle introuvable.");
                    return;
                }

                double poidsVide = poubelle.getPoidsActuel();

                String requete = "UPDATE poubelle SET poids_actuel = 0 WHERE idPoubelle = ?";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, idPoubelle);
                int lignes = statement.executeUpdate();

                if (lignes > 0) {
                    enregistrerHistoriqueVidage(idPoubelle, idCentre, poidsVide, menage.getIdentifiant());
                    System.out.println("La poubelle a été vidée avec succès dans le centre de tri sélectionné !");
                } else {
                    System.out.println("Aucune poubelle trouvée avec cet identifiant.");
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors du vidage de la poubelle : " + e.getMessage());
            }
        }
    }

    public static void afficherCentresDeTri() {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "SELECT * FROM centre_tri";
                PreparedStatement statement = connexion.prepareStatement(requete);
                ResultSet resultat = statement.executeQuery();

                System.out.println("Centres de tri disponibles :");
                while (resultat.next()) {
                    System.out.println("- ID : " + resultat.getInt("idCentre") +
                                       ", Nom : " + resultat.getString("nom") +
                                       ", Adresse : " + resultat.getString("adresse"));
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération des centres de tri : " + e.getMessage());
            }
        }
    }

    public static void enregistrerHistoriqueVidage(int idPoubelle, int idCentre, double poidsVide, int idEmploye) {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "INSERT INTO historique_vidages (idPoubelle, idCentre, idEmploye, poids_vidange, date_vidange) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setInt(1, idPoubelle);
                statement.setInt(2, idCentre); // ✅ Correction ici : idCentre au lieu de idCentreTri
                statement.setInt(3, idEmploye);
                statement.setDouble(4, poidsVide);
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'enregistrement de l'historique : " + e.getMessage());
            }
        }
    }

    public static void consulterHistoriqueVidages() {
        Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
        if (connexion != null) {
            try {
                String requete = "SELECT * FROM historique_vidages";
                PreparedStatement statement = connexion.prepareStatement(requete);
                ResultSet resultat = statement.executeQuery();

                System.out.println("Historique des vidages :");
                while (resultat.next()) {
                    System.out.println("- ID Historique : " + resultat.getInt("id") +
                                       ", ID Poubelle : " + resultat.getInt("idPoubelle") +
                                       ", ID Centre : " + resultat.getInt("idCentre") +
                                       ", Poids vidé : " + resultat.getDouble("poids_vidange") + " kg" +
                                       ", ID Employé : " + resultat.getInt("idEmploye") +
                                       ", Date : " + resultat.getTimestamp("date_vidange"));
                }

            } catch (SQLException e) {
                System.out.println("Erreur lors de la consultation de l'historique : " + e.getMessage());
            }
        }
    }


    public static void consulterHistoriqueDepots() {
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
             Statement statement = connexion.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, idPoubelle, idUtilisateur, poids_depose, date_depot FROM historique_depots")) {

            System.out.println("Historique des dépôts :");
            boolean historiqueVide = true;
            while (resultSet.next()) {
                historiqueVide = false;
                int idHistorique = resultSet.getInt("id");
                int idPoubelle = resultSet.getInt("idPoubelle");
                int idUtilisateur = resultSet.getInt("idUtilisateur");
                double poidsDepose = resultSet.getDouble("poids_depose");
                Timestamp dateDepot = resultSet.getTimestamp("date_depot");

                System.out.println("- ID Historique : " + idHistorique
                        + ", ID Poubelle : " + idPoubelle
                        + ", ID Utilisateur : " + idUtilisateur
                        + ", Poids déposé : " + poidsDepose + " kg"
                        + ", Date : " + dateDepot);
            }

            if (historiqueVide) {
                System.out.println("Aucun dépôt enregistré pour le moment.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la consultation de l'historique : " + e.getMessage());
        }
    }

}
