package modele;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceStatistiques {

    public static String genererRapport() {
        StringBuilder rapport = new StringBuilder();
        try (Connection conn = ConnexionBaseDeDonnees.obtenirConnexion()) {
            if (conn == null) return "Erreur : connexion à la base impossible.";

            int menages = count(conn, "menage");
            int corbeilles = count(conn, "corbeille");
            int dechets = count(conn, "dechet");
            int poubelles = count(conn, "poubelle");
            int bons = count(conn, "bons_achat");
            int vidages = count(conn, "vidage");

            rapport.append("📄 BILAN GLOBAL DE L'ENTREPRISE\n\n");
            rapport.append("👨‍👩‍👧‍👦 Ménages inscrits : ").append(menages).append("\n");
            rapport.append("🧺 Corbeilles : ").append(corbeilles).append("\n");
            rapport.append("♻️ Déchets enregistrés : ").append(dechets).append("\n");
            rapport.append("🗑️ Poubelles installées : ").append(poubelles).append("\n");
            rapport.append("🎫 Bons d'achat disponibles : ").append(bons).append("\n");
            rapport.append("🚛 Vidages enregistrés : ").append(vidages).append("\n\n");

            rapport.append("📦 Quantité de déchets par type :\n");
            for (Map.Entry<String, Number> e : getQuantiteDechetsParType().entrySet()) {
                rapport.append("- ").append(e.getKey()).append(" : ").append(e.getValue()).append(" kg\n");
            }

            rapport.append("\n🧾 Vidages par poubelle :\n");
            for (Map.Entry<String, Number> e : getNbVidagesParPoubelle().entrySet()) {
                rapport.append("- ").append(e.getKey()).append(" : ").append(e.getValue()).append("\n");
            }

            rapport.append("\n📅 Dépôts par jour :\n");
            for (Map.Entry<String, Number> e : getDepotsParJour().entrySet()) {
                rapport.append("- ").append(e.getKey()).append(" : ").append(e.getValue()).append(" kg\n");
            }

            rapport.append("\n🏆 Top 3 des types de déchets :\n");
            for (Map.Entry<String, Number> e : getTop3TypesDechets().entrySet()) {
                rapport.append("- ").append(e.getKey()).append(" : ").append(e.getValue()).append(" kg\n");
            }

        } catch (SQLException e) {
            return "Erreur lors de la génération du rapport : " + e.getMessage();
        }

        return rapport.toString();
    }

    private static int count(Connection conn, String table) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + table;
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public static Map<String, Number> getQuantiteDechetsParType() {
        return requeteMap("SELECT type_dechet, SUM(poids) AS total FROM dechet GROUP BY type_dechet", "type_dechet", "total");
    }

    public static Map<String, Number> getNbVidagesParPoubelle() {
        return requeteMap("SELECT idPoubelle, COUNT(*) AS nb FROM historique_vidages GROUP BY idPoubelle", "idPoubelle", "nb", "Poubelle ");
    }

    public static Map<String, Number> getDepotsParJour() {
        return requeteMap("SELECT DATE(date_depot) AS jour, SUM(poids_depose) AS total FROM historique_depots GROUP BY jour ORDER BY jour", "jour", "total");
    }

    public static Map<String, Number> getTop3TypesDechets() {
        return requeteMap("SELECT type_dechet, SUM(poids) AS total FROM dechet GROUP BY type_dechet ORDER BY total DESC LIMIT 3", "type_dechet", "total");
    }

    private static Map<String, Number> requeteMap(String sql, String keyCol, String valCol) {
        return requeteMap(sql, keyCol, valCol, "");
    }

    private static Map<String, Number> requeteMap(String sql, String keyCol, String valCol, String prefix) {
        Map<String, Number> map = new LinkedHashMap<>();
        try (Connection conn = ConnexionBaseDeDonnees.obtenirConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                map.put(prefix + rs.getString(keyCol), rs.getDouble(valCol));
            }
        } catch (SQLException e) {
            System.err.println("Erreur requeteMap : " + e.getMessage());
        }
        return map;
    }

    public static boolean exporterBaseEnCSV(String cheminFichier) {
        String[] tables = {"achats_bons", "menage", "corbeille", "poubelle", "dechet", "vidage", "bons_achat", "historique_depots", "historique_vidages"};
        try (Connection conn = ConnexionBaseDeDonnees.obtenirConnexion();
             PrintWriter writer = new PrintWriter(new FileWriter(cheminFichier))) {

            for (String table : tables) {
                writer.println("###TABLE=" + table);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();

                writer.println(IntStream.range(1, columnCount + 1)
                        .mapToObj(i -> {
                            try { return meta.getColumnName(i); } catch (SQLException e) { return ""; }
                        }).collect(Collectors.joining(",")));

                while (rs.next()) {
                    List<String> ligne = new ArrayList<>();
                    for (int i = 1; i <= columnCount; i++) {
                        ligne.add(rs.getString(i));
                    }
                    writer.println(String.join(",", ligne));
                }
                writer.println();
            }

            return true;
        } catch (Exception e) {
            System.err.println("Erreur export CSV complet : " + e.getMessage());
            return false;
        }
    }

    public static boolean importerBaseDepuisCSV(String cheminFichier) {
        try (Connection conn = ConnexionBaseDeDonnees.obtenirConnexion();
             Scanner scanner = new Scanner(new File(cheminFichier))) {

            conn.setAutoCommit(false);

            try (Statement disableFK = conn.createStatement()) {
                disableFK.execute("SET FOREIGN_KEY_CHECKS=0");
            }

            String currentTable = null;
            List<String> colonnes = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("###TABLE=")) {
                    currentTable = line.substring(9);
                    conn.prepareStatement("DELETE FROM " + currentTable).execute();
                    colonnes = null;
                    continue;
                }

                if (colonnes == null) {
                    colonnes = Arrays.asList(line.split(","));
                    continue;
                }

                String[] valeurs = line.split(",", -1);
                String placeholders = String.join(",", Collections.nCopies(valeurs.length, "?"));
                String sql = "INSERT INTO " + currentTable + " (" + String.join(",", colonnes) + ") VALUES (" + placeholders + ")";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    for (int i = 0; i < valeurs.length; i++) {
                        String val = valeurs[i].trim();
                        if (val.equalsIgnoreCase("null") || val.isEmpty() || val.equalsIgnoreCase("\\N")) {
                            stmt.setNull(i + 1, Types.NULL);
                        } else {
                            stmt.setString(i + 1, val);
                        }
                    }
                    stmt.executeUpdate();
                }
            }

            try (Statement enableFK = conn.createStatement()) {
                enableFK.execute("SET FOREIGN_KEY_CHECKS=1");
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            System.err.println("Erreur import CSV complet : " + e.getMessage());
            return false;
        }
    }

    public static boolean genererBilanTxt(String cheminFichier) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(cheminFichier))) {
            writer.println(genererRapport());
            return true;
        } catch (IOException e) {
            System.err.println("Erreur lors de la génération du fichier TXT : " + e.getMessage());
            return false;
        }
    }
}
