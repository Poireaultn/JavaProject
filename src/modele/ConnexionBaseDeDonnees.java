package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBaseDeDonnees {
    private static final String URL = "jdbc:mysql://localhost:3306/tri_selectif";
    private static final String UTILISATEUR = "root";
    private static final String MOT_DE_PASSE = "cytech0001";

    public static Connection obtenirConnexion() {
        try {
            return DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
        } catch (SQLException e) {
            return null;
        }
    }
}
